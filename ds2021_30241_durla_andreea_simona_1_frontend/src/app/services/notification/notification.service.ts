import {Injectable, OnDestroy } from '@angular/core';
import * as SockJS from 'sockjs-client';
import { CompatClient, Stomp } from '@stomp/stompjs';
import { AuthenticationService } from '../authentication/authentication.service';
import { Subject } from 'rxjs';
import { environment } from 'src/environments/environment';

export const WS_ENDPOINT = environment.wsEndpoint;
export const RECONNECT_INTERVAL = environment.reconnectInterval;

@Injectable({
  providedIn: 'root'
})
export class NotificationService implements OnDestroy {

  private stompClient!: CompatClient;
  public notifications$: Subject<any> = new Subject<any>();

  constructor (private authService: AuthenticationService) {
  }

  ngOnDestroy(): void {
    this.disconnect();
  }

  connect() {
    this.stompClient = Stomp.over(() => {
      return new SockJS(WS_ENDPOINT);
    });

    const clientId = this.authService.user.clientId;

    if(!clientId)
      return ;

    this.stompClient.reconnectDelay = RECONNECT_INTERVAL;

    const that = this;
    this.stompClient.connect({ clientId: clientId }, 
      () => {
        that.stompClient.subscribe(
          '/user/queue/notification', 
          (data) => { 
            this.notifications$.next(JSON.parse(data.body));
          }
        );
      },
      () => {
        setTimeout(() => {
            this.connect();
          },
          RECONNECT_INTERVAL
        );
      }
    );
  }

  disconnect() {
    if(this.stompClient !== undefined) {
      this.stompClient.disconnect();
    }
  }
}

