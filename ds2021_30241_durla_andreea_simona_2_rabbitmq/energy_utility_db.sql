PGDMP     $                
    y            d562bq8h1q1kn3     13.5 (Ubuntu 13.5-1.pgdg20.04+1)    13.4     ?           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            ?           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            ?           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            ?           1262    56675466    d562bq8h1q1kn3    DATABASE     c   CREATE DATABASE d562bq8h1q1kn3 WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.UTF-8';
    DROP DATABASE d562bq8h1q1kn3;
                iyrnacujymhozp    false            ?           0    0    DATABASE d562bq8h1q1kn3    ACL     A   REVOKE CONNECT,TEMPORARY ON DATABASE d562bq8h1q1kn3 FROM PUBLIC;
                   iyrnacujymhozp    false    4012            ?           0    0    SCHEMA public    ACL     ?   REVOKE ALL ON SCHEMA public FROM postgres;
REVOKE ALL ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO iyrnacujymhozp;
GRANT ALL ON SCHEMA public TO PUBLIC;
                   iyrnacujymhozp    false    5            ?           0    0    LANGUAGE plpgsql    ACL     1   GRANT ALL ON LANGUAGE plpgsql TO iyrnacujymhozp;
                   postgres    false    647            ?            1259    56682303    client    TABLE     ?   CREATE TABLE public.client (
    id bytea NOT NULL,
    address character varying(255) NOT NULL,
    birth_date timestamp without time zone NOT NULL,
    deleted boolean NOT NULL,
    name character varying(255) NOT NULL,
    user_id bytea NOT NULL
);
    DROP TABLE public.client;
       public         heap    iyrnacujymhozp    false            ?            1259    56682311    device    TABLE       CREATE TABLE public.device (
    id bytea NOT NULL,
    address character varying(255) NOT NULL,
    avg_energy_consumption real NOT NULL,
    deleted boolean NOT NULL,
    description character varying(255) NOT NULL,
    max_energy_consumption real NOT NULL,
    client_id bytea
);
    DROP TABLE public.device;
       public         heap    iyrnacujymhozp    false            ?            1259    56682319    end_user    TABLE     ?   CREATE TABLE public.end_user (
    id bytea NOT NULL,
    deleted boolean NOT NULL,
    password character varying(255) NOT NULL,
    role integer NOT NULL,
    username character varying(255) NOT NULL
);
    DROP TABLE public.end_user;
       public         heap    iyrnacujymhozp    false            ?            1259    56682327    monitored_value    TABLE     ?   CREATE TABLE public.monitored_value (
    id bytea NOT NULL,
    deleted boolean NOT NULL,
    energy_consumption real NOT NULL,
    "timestamp" timestamp without time zone NOT NULL,
    sensor_id bytea NOT NULL
);
 #   DROP TABLE public.monitored_value;
       public         heap    iyrnacujymhozp    false            ?            1259    56682335    sensor    TABLE     ?   CREATE TABLE public.sensor (
    id bytea NOT NULL,
    deleted boolean NOT NULL,
    description character varying(255) NOT NULL,
    max_value real,
    device_id bytea
);
    DROP TABLE public.sensor;
       public         heap    iyrnacujymhozp    false            ?          0    56682303    client 
   TABLE DATA           Q   COPY public.client (id, address, birth_date, deleted, name, user_id) FROM stdin;
    public          iyrnacujymhozp    false    200   ?        ?          0    56682311    device 
   TABLE DATA           ~   COPY public.device (id, address, avg_energy_consumption, deleted, description, max_energy_consumption, client_id) FROM stdin;
    public          iyrnacujymhozp    false    201   `!       ?          0    56682319    end_user 
   TABLE DATA           I   COPY public.end_user (id, deleted, password, role, username) FROM stdin;
    public          iyrnacujymhozp    false    202   g"       ?          0    56682327    monitored_value 
   TABLE DATA           b   COPY public.monitored_value (id, deleted, energy_consumption, "timestamp", sensor_id) FROM stdin;
    public          iyrnacujymhozp    false    203   ?#       ?          0    56682335    sensor 
   TABLE DATA           P   COPY public.sensor (id, deleted, description, max_value, device_id) FROM stdin;
    public          iyrnacujymhozp    false    204   ?#                  2606    56682310    client client_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.client
    ADD CONSTRAINT client_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.client DROP CONSTRAINT client_pkey;
       public            iyrnacujymhozp    false    200                       2606    56682318    device device_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.device
    ADD CONSTRAINT device_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.device DROP CONSTRAINT device_pkey;
       public            iyrnacujymhozp    false    201                       2606    56682326    end_user end_user_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.end_user
    ADD CONSTRAINT end_user_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.end_user DROP CONSTRAINT end_user_pkey;
       public            iyrnacujymhozp    false    202                       2606    56682334 $   monitored_value monitored_value_pkey 
   CONSTRAINT     b   ALTER TABLE ONLY public.monitored_value
    ADD CONSTRAINT monitored_value_pkey PRIMARY KEY (id);
 N   ALTER TABLE ONLY public.monitored_value DROP CONSTRAINT monitored_value_pkey;
       public            iyrnacujymhozp    false    203                       2606    56682342    sensor sensor_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.sensor
    ADD CONSTRAINT sensor_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.sensor DROP CONSTRAINT sensor_pkey;
       public            iyrnacujymhozp    false    204                       2606    56682344 %   end_user uk_ihdhmp1sy4geiyrdi4l2oosjx 
   CONSTRAINT     d   ALTER TABLE ONLY public.end_user
    ADD CONSTRAINT uk_ihdhmp1sy4geiyrdi4l2oosjx UNIQUE (username);
 O   ALTER TABLE ONLY public.end_user DROP CONSTRAINT uk_ihdhmp1sy4geiyrdi4l2oosjx;
       public            iyrnacujymhozp    false    202                       2606    56682360 "   sensor fk487e3tii2n3mbs04r70ncmjs2    FK CONSTRAINT     ?   ALTER TABLE ONLY public.sensor
    ADD CONSTRAINT fk487e3tii2n3mbs04r70ncmjs2 FOREIGN KEY (device_id) REFERENCES public.device(id);
 L   ALTER TABLE ONLY public.sensor DROP CONSTRAINT fk487e3tii2n3mbs04r70ncmjs2;
       public          iyrnacujymhozp    false    204    201    3859                       2606    56682355 +   monitored_value fk4ks666h8yy66og1f3ak9hr5vb    FK CONSTRAINT     ?   ALTER TABLE ONLY public.monitored_value
    ADD CONSTRAINT fk4ks666h8yy66og1f3ak9hr5vb FOREIGN KEY (sensor_id) REFERENCES public.sensor(id);
 U   ALTER TABLE ONLY public.monitored_value DROP CONSTRAINT fk4ks666h8yy66og1f3ak9hr5vb;
       public          iyrnacujymhozp    false    203    3867    204                       2606    56682350 "   device fkdu2w3cqp9s5nydbum1dkl1wcb    FK CONSTRAINT     ?   ALTER TABLE ONLY public.device
    ADD CONSTRAINT fkdu2w3cqp9s5nydbum1dkl1wcb FOREIGN KEY (client_id) REFERENCES public.client(id);
 L   ALTER TABLE ONLY public.device DROP CONSTRAINT fkdu2w3cqp9s5nydbum1dkl1wcb;
       public          iyrnacujymhozp    false    200    201    3857                       2606    56682345 "   client fkeknxmydvkub51ble3npkqhgue    FK CONSTRAINT     ?   ALTER TABLE ONLY public.client
    ADD CONSTRAINT fkeknxmydvkub51ble3npkqhgue FOREIGN KEY (user_id) REFERENCES public.end_user(id);
 L   ALTER TABLE ONLY public.client DROP CONSTRAINT fkeknxmydvkub51ble3npkqhgue;
       public          iyrnacujymhozp    false    200    3861    202            ?   ?   x?M?Mj?@???????WRv]v?v6???b?I\r?L[
????n??,?h??
FҤ?d????ޤ?x?v|?`?y??FgO ??e??m????)CΏ}Ut?Ż?֓??Qk??zkz??XK?5XxX-K??b?X??????K??/? ??w???M???UƷ}?[9?Wt?H1?D??b?)q"?h??1?	Q?G}      ?   ?   x?Uϻj1??Z???.#??̥N?v??4A66k)?<}?????g]?&p??!? `?D?=?h$׌:???s??Y??TU??r>}??5????c??2vޅ?.?Yv?coM??u????Ek???w??G	"?,e??Đ3p,6?X?@?Y)?HU?;??%?????Q?K???:??1?-??v ?HIP\???|M??܀J?? ????I???J?܏????B?-? ?J	?bp? ??ӺL???n?      ?     x?M??v?0 ?5|?됄L,??*u??u:l^HT???__]?뻸y~UJi?Bj?a??U?
?ZD ?{?G?Gp???+??e??[+7????~+?yG]??-Z?t???ʶ??????ީ?A??????U???T?"?L?P4?ҚBc?]?>?薜???????l????'<???7F]&x?|9??%?d?j?SV???cQ?J?#" V?q 8R"*???k?Y??fV?fyi?k??r??)_;[]2??b<??Pb2????
P?*[??#????c?      ?      x?????? ? ?      ?   ?   x?U?1n?0??Y:EN`?E???ꅒH ??°? ???- Ǉ?㺾 Ԧ`?P??պLK?gA??×}??Ǵ??[?,?u}5??L}";i'????hM<?k㉳;?u?(u4?H?,
?Q???|zX ?K?Q?nl???t???¿?B????*wK807o?h?*??;}?m???q? ???f?t&??خG???V?l??????? ?5V
     