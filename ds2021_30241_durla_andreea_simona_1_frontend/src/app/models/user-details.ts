export interface UserDetails {
    token: string;
    id: string
    username: string;
    roles: string[];
    clientId?: string;
}
