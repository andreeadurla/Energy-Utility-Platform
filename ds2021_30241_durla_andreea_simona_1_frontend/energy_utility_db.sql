PGDMP                     
    y            d3tjocmle291lv     13.4 (Ubuntu 13.4-4.pgdg20.04+1)    13.4     �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    12158068    d3tjocmle291lv    DATABASE     c   CREATE DATABASE d3tjocmle291lv WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.UTF-8';
    DROP DATABASE d3tjocmle291lv;
                vzcqfjouplvtez    false            �           0    0    DATABASE d3tjocmle291lv    ACL     A   REVOKE CONNECT,TEMPORARY ON DATABASE d3tjocmle291lv FROM PUBLIC;
                   vzcqfjouplvtez    false    4012            �           0    0    SCHEMA public    ACL     �   REVOKE ALL ON SCHEMA public FROM postgres;
REVOKE ALL ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO vzcqfjouplvtez;
GRANT ALL ON SCHEMA public TO PUBLIC;
                   vzcqfjouplvtez    false    5            �           0    0    LANGUAGE plpgsql    ACL     1   GRANT ALL ON LANGUAGE plpgsql TO vzcqfjouplvtez;
                   postgres    false    647            �            1259    21344651    client    TABLE     �   CREATE TABLE public.client (
    id bytea NOT NULL,
    address character varying(255),
    birth_date timestamp without time zone,
    deleted boolean NOT NULL,
    name character varying(255),
    user_id bytea
);
    DROP TABLE public.client;
       public         heap    vzcqfjouplvtez    false            �            1259    21344659    device    TABLE       CREATE TABLE public.device (
    id bytea NOT NULL,
    address character varying(255) NOT NULL,
    avg_energy_consumption real NOT NULL,
    deleted boolean NOT NULL,
    description character varying(255) NOT NULL,
    max_energy_consumption real NOT NULL,
    client_id bytea
);
    DROP TABLE public.device;
       public         heap    vzcqfjouplvtez    false            �            1259    21344667    end_user    TABLE     �   CREATE TABLE public.end_user (
    id bytea NOT NULL,
    deleted boolean NOT NULL,
    password character varying(255) NOT NULL,
    role integer NOT NULL,
    username character varying(255) NOT NULL
);
    DROP TABLE public.end_user;
       public         heap    vzcqfjouplvtez    false            �            1259    21344675    monitored_value    TABLE     �   CREATE TABLE public.monitored_value (
    id bytea NOT NULL,
    deleted boolean NOT NULL,
    energy_consumption real,
    "timestamp" timestamp without time zone,
    sensor_id bytea
);
 #   DROP TABLE public.monitored_value;
       public         heap    vzcqfjouplvtez    false            �            1259    21344683    sensor    TABLE     �   CREATE TABLE public.sensor (
    id bytea NOT NULL,
    deleted boolean NOT NULL,
    description character varying(255) NOT NULL,
    max_value real,
    device_id bytea
);
    DROP TABLE public.sensor;
       public         heap    vzcqfjouplvtez    false            �          0    21344651    client 
   TABLE DATA           Q   COPY public.client (id, address, birth_date, deleted, name, user_id) FROM stdin;
    public          vzcqfjouplvtez    false    200   J        �          0    21344659    device 
   TABLE DATA           ~   COPY public.device (id, address, avg_energy_consumption, deleted, description, max_energy_consumption, client_id) FROM stdin;
    public          vzcqfjouplvtez    false    201   �"       �          0    21344667    end_user 
   TABLE DATA           I   COPY public.end_user (id, deleted, password, role, username) FROM stdin;
    public          vzcqfjouplvtez    false    202   ;$       �          0    21344675    monitored_value 
   TABLE DATA           b   COPY public.monitored_value (id, deleted, energy_consumption, "timestamp", sensor_id) FROM stdin;
    public          vzcqfjouplvtez    false    203   �&       �          0    21344683    sensor 
   TABLE DATA           P   COPY public.sensor (id, deleted, description, max_value, device_id) FROM stdin;
    public          vzcqfjouplvtez    false    204   ^,                  2606    21344658    client client_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.client
    ADD CONSTRAINT client_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.client DROP CONSTRAINT client_pkey;
       public            vzcqfjouplvtez    false    200                       2606    21344666    device device_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.device
    ADD CONSTRAINT device_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.device DROP CONSTRAINT device_pkey;
       public            vzcqfjouplvtez    false    201                       2606    21344674    end_user end_user_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.end_user
    ADD CONSTRAINT end_user_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.end_user DROP CONSTRAINT end_user_pkey;
       public            vzcqfjouplvtez    false    202                       2606    21344682 $   monitored_value monitored_value_pkey 
   CONSTRAINT     b   ALTER TABLE ONLY public.monitored_value
    ADD CONSTRAINT monitored_value_pkey PRIMARY KEY (id);
 N   ALTER TABLE ONLY public.monitored_value DROP CONSTRAINT monitored_value_pkey;
       public            vzcqfjouplvtez    false    203                       2606    21344690    sensor sensor_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.sensor
    ADD CONSTRAINT sensor_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.sensor DROP CONSTRAINT sensor_pkey;
       public            vzcqfjouplvtez    false    204                       2606    21344692 %   end_user uk_ihdhmp1sy4geiyrdi4l2oosjx 
   CONSTRAINT     d   ALTER TABLE ONLY public.end_user
    ADD CONSTRAINT uk_ihdhmp1sy4geiyrdi4l2oosjx UNIQUE (username);
 O   ALTER TABLE ONLY public.end_user DROP CONSTRAINT uk_ihdhmp1sy4geiyrdi4l2oosjx;
       public            vzcqfjouplvtez    false    202                       2606    21344708 "   sensor fk487e3tii2n3mbs04r70ncmjs2    FK CONSTRAINT     �   ALTER TABLE ONLY public.sensor
    ADD CONSTRAINT fk487e3tii2n3mbs04r70ncmjs2 FOREIGN KEY (device_id) REFERENCES public.device(id);
 L   ALTER TABLE ONLY public.sensor DROP CONSTRAINT fk487e3tii2n3mbs04r70ncmjs2;
       public          vzcqfjouplvtez    false    204    201    3859                       2606    21344703 +   monitored_value fk4ks666h8yy66og1f3ak9hr5vb    FK CONSTRAINT     �   ALTER TABLE ONLY public.monitored_value
    ADD CONSTRAINT fk4ks666h8yy66og1f3ak9hr5vb FOREIGN KEY (sensor_id) REFERENCES public.sensor(id);
 U   ALTER TABLE ONLY public.monitored_value DROP CONSTRAINT fk4ks666h8yy66og1f3ak9hr5vb;
       public          vzcqfjouplvtez    false    203    3867    204                       2606    21344698 "   device fkdu2w3cqp9s5nydbum1dkl1wcb    FK CONSTRAINT     �   ALTER TABLE ONLY public.device
    ADD CONSTRAINT fkdu2w3cqp9s5nydbum1dkl1wcb FOREIGN KEY (client_id) REFERENCES public.client(id);
 L   ALTER TABLE ONLY public.device DROP CONSTRAINT fkdu2w3cqp9s5nydbum1dkl1wcb;
       public          vzcqfjouplvtez    false    200    201    3857                       2606    21344693 "   client fkeknxmydvkub51ble3npkqhgue    FK CONSTRAINT     �   ALTER TABLE ONLY public.client
    ADD CONSTRAINT fkeknxmydvkub51ble3npkqhgue FOREIGN KEY (user_id) REFERENCES public.end_user(id);
 L   ALTER TABLE ONLY public.client DROP CONSTRAINT fkeknxmydvkub51ble3npkqhgue;
       public          vzcqfjouplvtez    false    200    3861    202            �   6  x�]��n�0E��W��`���9)
t�$���_� W*d����ԸiH��=:��pxu�R	�Đ�:l��v�?Ϛ�{��2���4_��a�!p���ЮM��y���n�W5�+�P,d�� ������ݶ3��A��+G��d����6���&=�t�mgn���mG�»�?����R��.��%Y�9[[	�J0.:��Zve�@g5H�#P�5fP
��`���%��q�o�2��G���9(�t�N�A�xj7{�Ω�f�R|Ռ%,\KL�92E���b��7�g�B�=���N����1��z�(��ı�v�2G])V�փ�h�ť���"Ur](��59#�,�@r!s���ݟB�̗�m8��aQ�jp+x�t���M�Y�~7�FF�ى]���=��$�A���TI���X�D%(E*�JېqZ�O���E�$�CùB�o:���:��QC%R�6 a��H�r��W��
����6���LE4�8xs�����<����[H���_j�.�y�v�}T;i��`
������YA����v�(]�      �   �  x����n�0�g�)� �@�)rLڹ-Х-��$@��Z
�<})d�.����y<��J�\l�HU���E80'�����vά�(�)��������������M/|�:��z-˵t^�ޚ"3��x|5`�X_	Eg$�h���0����&Pp���q��0~4�����?��e-�6�u[������I��F����h�7>ڈB�q��,)��<R�#�7a�@��A��n���z�w ��ޫȨ��pg�--����%��ng���	?kVLΐCG�2%���4�g�f�q��L.���ęW�v�O2�,\���a�4s��D�T!��`>�ϧ��
i,]��1{m����\��s��g1e,�K	����Xjɀg��d?����o��v�����QV������I��p�:��*      �   �  x�M�˒�0���Ƅ\H�^��b��@Bؠ���55�,�&�����|}5�TfƒJ$�!nF�C�-�d/��ѻS__�K�2�$�"bU�>����>������~���6.�z�Kz���� 1.���뫱P�Ӗ����R
��V�FT���>��}��§�8��)w5�{s����x�Y|;��"<۩������-�;:/lw��"$�X�B�,dBh��p�_޲��f3��M��g������/�<s�A�<PF��dF�߭��M���>��F¢�p��@PP�kmŠ����E��~}�G'�oO~��l���q�QZ��@ǫ]�ek���o�6�.��H��$)q�i[%p4�\��Ԡb�46��_��l�`ӷ�t�.��:����w��vs5T�fj���+F�)�q_3��:շrκ�@�)QL*�H�B�G� ��z���-j����c���b20Џ�Mj��dD/�|"Cا�Y$�u��oՂyg�c�2�8o����dTRW �0Q������-�8��z2t�����a��k�Y8�Xl��hs*i��zP��	9eyTw����$����L�yhi@+@��q����>�S9�\�ɜf��g���j�=/���Q3�/��>��0:��ӈNI}�Z�x{{�
o�      �   c  x���M�[9���)r���#�ϒ�DIg��̴_&@��v��ɪR���[y�f]5Yu/�%�3M�R��o�.�
��������o�~��Ye�Ea����Y��ɴ���L���EŹ�Y���t_��� �ȝ�g�a�9r��E�n;�q}2�O��0H��Z��b�������gNbCˣ�!�G���m�e����x�Ҷ�3a��T�� �Г*�bsƬ�޸L�dW��.D<�q����s���<wTU(��6N7�QVC���1M���D����+����á�6i��ש��Ξ68n]��qT����i՘O�ʹfp���o�õX�� o޶騣/R��� �7��<����6�Te�����O���tY;��Y�{_�=5Jk��Ѡ!A��UEܶ���#s�Ю�Ds�	�=���D�{�����m#��
����^4�7z"�đBaf�b�d��굏����3�^jNtc؇��rcrb�x�����Բ�E:��!�:��!T�0�n|-�c�NQ����C��|R�l��WO�h{�ⵊ���4z2�}��8t���V[4���*-��ME�b�q�"]j�eb���˴�4`M^~��gK*�ļH��y-�]�z�"��"p��^d�؍Q�P.��n!�}��Y�͂Ξo9�:���K�m����ħ=��&���v��$t�4:D5��K�n�g���޺BB�7H�4xD�����W����%�_Ɇ�\=FOZ�-���g����a�wC��4EJ530uh|�V�k��7��
��> ;�C��M��9��?�8�4�Ҕ�(m�b}Ʉ�'�Q�kV�f�q��܈�5����Lk-M�k��˨WB���'?��'�S�<����T��)�rV���0���!ԣl�l� �m,X��r-��cz����\�F�d�֥8���,�%�\�MO���?��
aL,T|�.���
�>}ɓ�n�B.��"8,b�5�e�5���C6^זr@�Qs
,h��!@�W#��\9dOِA�� �w�jx�G��[��1_�vL�i��������zg���kㄿ��t�x����4ߍւx�R\��Z	���:��r]���@'�f �#Z��C�W�pÄ�s�{�K9�a�UM J���ǣ���[*)����>��徴rX`/���JTqK���� �ut{��~���b%� ��+��3�,��Q���w}��C_A`���n��{iV7�UGz���fD_:(D��P�0� ҝ"@��"��e/���5��������k���t����T�X�X�Q$����qr�W�qC�	���Ќ����#h
\���"nVJ_?������`gq�      �   |  x�U�1�1E��)�J$%�i���"��@�,fm���1�u���?�׶�ث�+Y�A�)Fͥc��|�g����?<>����>� ��l�.���}�Z��r�y���:c;	h��~f�%�N��y�\�%p�ft�}��+v}<w]ִL�4�=�p�aY�3�n	�&s�X� T+b�LVC$%;���m��Xn{�KNbM3	�.D���kNh���*z��@` �@j���Z?�?oW�>���W�#�M^�xN�a. �1�i�7�ʑ=��:��S!���d�{���!���Pv4.�D0�!͋���j���̐�	8�r��Dvc�ٯX7,ũbQ(������DRPu"��(#L\TN����>	���|��r��F��     