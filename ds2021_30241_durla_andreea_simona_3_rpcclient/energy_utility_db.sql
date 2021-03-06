PGDMP     %    *                y            dbhe1f9cohp5nb     13.5 (Ubuntu 13.5-2.pgdg20.04+1)    13.4     ?           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            ?           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            ?           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            ?           1262    31751    dbhe1f9cohp5nb    DATABASE     c   CREATE DATABASE dbhe1f9cohp5nb WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'en_US.UTF-8';
    DROP DATABASE dbhe1f9cohp5nb;
                fpcdntakwmetom    false            ?           0    0    DATABASE dbhe1f9cohp5nb    ACL     A   REVOKE CONNECT,TEMPORARY ON DATABASE dbhe1f9cohp5nb FROM PUBLIC;
                   fpcdntakwmetom    false    4012            ?           0    0    SCHEMA public    ACL     ?   REVOKE ALL ON SCHEMA public FROM postgres;
REVOKE ALL ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO fpcdntakwmetom;
GRANT ALL ON SCHEMA public TO PUBLIC;
                   fpcdntakwmetom    false    5            ?           0    0    LANGUAGE plpgsql    ACL     1   GRANT ALL ON LANGUAGE plpgsql TO fpcdntakwmetom;
                   postgres    false    647            ?            1259    32652    client    TABLE     ?   CREATE TABLE public.client (
    id bytea NOT NULL,
    address character varying(255) NOT NULL,
    birth_date timestamp without time zone NOT NULL,
    deleted boolean NOT NULL,
    name character varying(255) NOT NULL,
    user_id bytea NOT NULL
);
    DROP TABLE public.client;
       public         heap    fpcdntakwmetom    false            ?            1259    32658    device    TABLE       CREATE TABLE public.device (
    id bytea NOT NULL,
    address character varying(255) NOT NULL,
    avg_energy_consumption real NOT NULL,
    deleted boolean NOT NULL,
    description character varying(255) NOT NULL,
    max_energy_consumption real NOT NULL,
    client_id bytea
);
    DROP TABLE public.device;
       public         heap    fpcdntakwmetom    false            ?            1259    32664    end_user    TABLE     ?   CREATE TABLE public.end_user (
    id bytea NOT NULL,
    deleted boolean NOT NULL,
    password character varying(255) NOT NULL,
    role integer NOT NULL,
    username character varying(255) NOT NULL
);
    DROP TABLE public.end_user;
       public         heap    fpcdntakwmetom    false            ?            1259    32670    monitored_value    TABLE     ?   CREATE TABLE public.monitored_value (
    id bytea NOT NULL,
    deleted boolean NOT NULL,
    energy_consumption real NOT NULL,
    "timestamp" timestamp without time zone NOT NULL,
    sensor_id bytea NOT NULL
);
 #   DROP TABLE public.monitored_value;
       public         heap    fpcdntakwmetom    false            ?            1259    32676    sensor    TABLE     ?   CREATE TABLE public.sensor (
    id bytea NOT NULL,
    deleted boolean NOT NULL,
    description character varying(255) NOT NULL,
    max_value real,
    device_id bytea
);
    DROP TABLE public.sensor;
       public         heap    fpcdntakwmetom    false            ?          0    32652    client 
   TABLE DATA           Q   COPY public.client (id, address, birth_date, deleted, name, user_id) FROM stdin;
    public          fpcdntakwmetom    false    200   J        ?          0    32658    device 
   TABLE DATA           ~   COPY public.device (id, address, avg_energy_consumption, deleted, description, max_energy_consumption, client_id) FROM stdin;
    public          fpcdntakwmetom    false    201   !!       ?          0    32664    end_user 
   TABLE DATA           I   COPY public.end_user (id, deleted, password, role, username) FROM stdin;
    public          fpcdntakwmetom    false    202   '"       ?          0    32670    monitored_value 
   TABLE DATA           b   COPY public.monitored_value (id, deleted, energy_consumption, "timestamp", sensor_id) FROM stdin;
    public          fpcdntakwmetom    false    203   N#       ?          0    32676    sensor 
   TABLE DATA           P   COPY public.sensor (id, deleted, description, max_value, device_id) FROM stdin;
    public          fpcdntakwmetom    false    204   ??                  2606    32683    client client_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.client
    ADD CONSTRAINT client_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.client DROP CONSTRAINT client_pkey;
       public            fpcdntakwmetom    false    200                       2606    32685    device device_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.device
    ADD CONSTRAINT device_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.device DROP CONSTRAINT device_pkey;
       public            fpcdntakwmetom    false    201                       2606    32687    end_user end_user_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.end_user
    ADD CONSTRAINT end_user_pkey PRIMARY KEY (id);
 @   ALTER TABLE ONLY public.end_user DROP CONSTRAINT end_user_pkey;
       public            fpcdntakwmetom    false    202                       2606    32689 $   monitored_value monitored_value_pkey 
   CONSTRAINT     b   ALTER TABLE ONLY public.monitored_value
    ADD CONSTRAINT monitored_value_pkey PRIMARY KEY (id);
 N   ALTER TABLE ONLY public.monitored_value DROP CONSTRAINT monitored_value_pkey;
       public            fpcdntakwmetom    false    203                       2606    32691    sensor sensor_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.sensor
    ADD CONSTRAINT sensor_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.sensor DROP CONSTRAINT sensor_pkey;
       public            fpcdntakwmetom    false    204                       2606    32693 %   end_user uk_ihdhmp1sy4geiyrdi4l2oosjx 
   CONSTRAINT     d   ALTER TABLE ONLY public.end_user
    ADD CONSTRAINT uk_ihdhmp1sy4geiyrdi4l2oosjx UNIQUE (username);
 O   ALTER TABLE ONLY public.end_user DROP CONSTRAINT uk_ihdhmp1sy4geiyrdi4l2oosjx;
       public            fpcdntakwmetom    false    202                       2606    32694 "   sensor fk487e3tii2n3mbs04r70ncmjs2    FK CONSTRAINT     ?   ALTER TABLE ONLY public.sensor
    ADD CONSTRAINT fk487e3tii2n3mbs04r70ncmjs2 FOREIGN KEY (device_id) REFERENCES public.device(id);
 L   ALTER TABLE ONLY public.sensor DROP CONSTRAINT fk487e3tii2n3mbs04r70ncmjs2;
       public          fpcdntakwmetom    false    201    3859    204                       2606    32699 +   monitored_value fk4ks666h8yy66og1f3ak9hr5vb    FK CONSTRAINT     ?   ALTER TABLE ONLY public.monitored_value
    ADD CONSTRAINT fk4ks666h8yy66og1f3ak9hr5vb FOREIGN KEY (sensor_id) REFERENCES public.sensor(id);
 U   ALTER TABLE ONLY public.monitored_value DROP CONSTRAINT fk4ks666h8yy66og1f3ak9hr5vb;
       public          fpcdntakwmetom    false    204    203    3867                       2606    32704 "   device fkdu2w3cqp9s5nydbum1dkl1wcb    FK CONSTRAINT     ?   ALTER TABLE ONLY public.device
    ADD CONSTRAINT fkdu2w3cqp9s5nydbum1dkl1wcb FOREIGN KEY (client_id) REFERENCES public.client(id);
 L   ALTER TABLE ONLY public.device DROP CONSTRAINT fkdu2w3cqp9s5nydbum1dkl1wcb;
       public          fpcdntakwmetom    false    200    3857    201                       2606    32709 "   client fkeknxmydvkub51ble3npkqhgue    FK CONSTRAINT     ?   ALTER TABLE ONLY public.client
    ADD CONSTRAINT fkeknxmydvkub51ble3npkqhgue FOREIGN KEY (user_id) REFERENCES public.end_user(id);
 L   ALTER TABLE ONLY public.client DROP CONSTRAINT fkeknxmydvkub51ble3npkqhgue;
       public          fpcdntakwmetom    false    200    3861    202            ?   ?   x?M?Mj?@???????WRv]v?v6???b?I\r?L[
????n??,?h??
FҤ?d????ޤ?x?v|?`?y??FgO ??e??m????)CΏ}Ut?Ż?֓??Qk??zkz??XK?5XxX-K??b?X??????K??/? ??w???M???UƷ}?[9?Wt?H1?D??b?)q"?h??1?	Q?G}      ?   ?   x???=N?0@??9EN?g???,܀6??K???6Zqz?:?W|z?z5??fs%2?9E#?f?Q=???'e?^??????????|?羏y???y??lgٹ??5e0.???j+k????F%?H4h????!FJ??BE???k?&?c?ps?+???"?<???ֹ?U_?% ])C???Đ3p,6?X?@?Y?P?7??!?????D?.?7?????h_???`????ox?      ?     x?M??v?0 ?5|?됄L,??*u??u:l^HT???__]?뻸y~UJi?Bj?a??U?
?ZD ?{?G?Gp???+??e??[+7????~+?yG]??-Z?t???ʶ??????ީ?A??????U???T?"?L?P4?ҚBc?]?>?薜???????l????'<???7F]&x?|9??%?d?j?SV???cQ?J?#" V?q 8R"*???k?Y??fV?fyi?k??r??)_;[]2??b<??Pb2????
P?*[??#????c?      ?      x??}[???m???(2???$??/??!??U??dI~?x{w??W5%?A?_???)>?PF??Zi}?"^j?S??X?p?.?????]???)?O	W	????Ի??Ro?I?PVI?6K?????>]??z?3????,??8[u??q?J??????
ˢ?8?]??%??}u@?(???y%3?ج??V?b??f?+M5'|Z???թ????s?ף???G?qti?1j/?p?s?^)?C~a}^WH?RmQU?h?e?֢[?\J?|C?]!?Kz???Ãse??{H?4?6_??+Z??ѧ/?????k??IrxY??Uz?bڀ??4i???}]c?k?P?J???մ?oa?#V?~???????r???T???R )cXS?`?4D|?s???6V-????\?x_?uuߚL??7?eW?????R?ލ??:?/?D?:s?`2??%_9??Pr?????]Ã?˲?Z?ēԥ[j?#???l^oK?/a??.?K??????%..?O0?+??9<?ҁF?/???g4q}???i?VV|!?Y^P???C??ǭ??rrK??6??l????.?S?O#?ߗ??(??5$l??{?9Y়s?????b???}oY?`.?GqnU???3??F??O??????e??FiA?I?R񾢶Ud??V?~7???󲚧3?o)?4?&cV?????F??^????-?|_ب?%?[o???kŞ?????}?r????????ln?Us?8R?@(=?T?;?Ɗ?ʱ??!ځר	??7_ޖ?&???:bs?~?B彏???y]^??@G;???a????yw????\?? &??P??0F?Y?Z^K"=L?bi?R?za}?????:(?o?PY?? 6Ak󶍒.??|???k??*?q+?91ZDŻ?pW>G 0&;0?d?????+'	 ??8lhg?G˽?k?>??5??@???p??g.h???Q)??b???de???s??????
????!?K@?f6?z:??? ???:?9?M$lڠ?"??`ݻ??%p??d??????? ??^O?Z?7<\;?<x?,????l8??+??:D?X?WĮ*do_??!?<???;?j?]?챖e??J????ػ??2T????
??L??y??s?{+?????_?U?????C??jIk%J?N??6???2<ԓ ???(l?"t?\DJ?b?/??	???O?e?'dc?+T?F?&???	`q?"??zY?O?8 n??h??5?P?1????????ښ?\????yY}?էK?uPl???!??n?C,??SC?5*t????v?;>a)t8??????	?+????j????mU?????Ei??L?	*?+?r?T?
?%Ng??????To?`[??^????.S???V?();??]?z?%\|??&??`ӽᓠj?????ߍ??????{!}?[yN<????X?oP<ǹN](?`~c~??p???9)^X!????@?!a??W??\????????,??mHE?7W??,???V(?_p?????ѐ?z[?'???h~??%??????.^?E_Xߝ?A9??LT8+W?4?I(<???Sx?$?k??=ⱄܭa?k?n??տ?B~?ݓ?a??A"??+?I??^H8???JM?8?=S??!C\?ւb?2wΰ,?????Ҍt?1??VV#????=???Ҽ??M??`k9u#?U?> S?W????0[?~??V?Kza}ג>???L1:?r߂i??U#??K??`k?	?c?UA	??ϐ??dbq?1?wi*.???o?e??I??Ŝ?K?Ogg?`A-<??4=???]?Q??ޞ?|?LO?>^?^?I:??F???%?9?19 ,??u????>?N҅??e???&??? ??'
I?v???????-????Bf?>G??e?j~????P????#(a?0?
??Xjd,???㪥?w?UJ??<?Hcn????wWkJ????8 ???m???A???????X????????[ f??Y?b???݃ͷ?ݶ&3?>7?_???'>Y? d9_H/???????|/???߇?%tG?;?0?W?k:???Sy?ꀽ'??	&苂I???;԰?t%??:?/???-0?svK ?
L?6?H0?B?[????????????x??8????9?H???Y?.?????U?6??.|)"X
㞧??R?P????Gy ??? ???????;젇u?????wJGy?	??????k_?դ??p?|]? 7?4??<!?\??\?Vh???]??????m???N1??V?s?M?92&m?f??Я???	0??R?O=???w+?v?~?;??k???VPZ? ????(G??/???UG?|??I?0v??\? ?C?\E-?Ql?8??Hza}w?cA?+?
`??B????|k?X??d3'I?>?p?)ۀ?C??4?8)7???????5o/#????"?䄳?????,???P%'Y?1`0C??sAϪ?]aOX????X%^?bxa}??B??#??f(?2?M[?Ow8?xa????'iB??????$?-J??)#4?~_?txH/??gA=?՝\??ͳD?~?{B0q???3k~b?(??MŬ?ʌ?uR??дA?o,x?$????+?8̮?6??ZK?~?X?I?> ????????$?s?=?{?.???T??3.?@YT_Xߩ?e8
H?<"4[?8??^ǎ]0?/????k?-??s???3D?ďX?X?3?.yRГ<a???tUVO??9bj??.?{Y<w??x?'i????)85???yW_5??@?d??q2<e?I?P! S[?v$i5?8~	??f?X?AP?ox?(?̔?AUk??  ???7lG?????????oޘ\E??
??#?8?ƽ.Xa???u?5??xF??9G?S??3??$??t??F????=(???\?Ɓ}?Q?輡???쭓<!???`??\??M???P?X*??G&
c??w)??P??1?U??uP?ֶ??ٮG?$%
]??U??%?Z??QC1???e??X?9????4?XZ??W???@? ?Y???.-V?Jy`????^?X?=??kp?)??'?y	??????ʜQ\?Sb????M5???N?{??*&?u?????B??2	?%?}????.(ה???$h?????????^??!??r?$???/??a?ȭ???^??gԀ=??w??y??_X???Տ԰#???cj??c5E??%??O?4?"????bB???Y]??????l,????????b?\???^?5]՗>G??v??Wp???|?-Ċ|XM`#]KY 3? ??7?H&?~?>??Dmb+繺 ??C??6c???$??????K?=6?? ?1??@?k=?Guc!??????S?:?F??P??	b?%??9/????0?+???u?0?#,ɐ?xk+??ˋ+?X)^>?7?wf??R?? ʡ??w??4g?)d,?Z
??: ?.Y?!?]$?a?U?į
?s?E^?߈???|?3???r?cAQ???
??f???Ex%\???u@䣤??!???5c????y?a]1??w88?ރC)Θ?߮S??	 ??.??u?4??V7??6G???:bL?d???块?w;d?ь??P??g?P???`?[??`W(??7N???????k?k?????0?u???3?\???A?XuO-???TZo-6h>X?؇?>'?????P??;3 ???Z??????0m	?t?F???????􂀂G??&4?g
K?R??3̊?|????ab?5??1E??5aq?J??+߉ì?????{R>3"?;I?A??"\??#r#?l??bJIO?8??(?0<???c	Ƃ?2??|.?????=~	8????է????	l??ǍEI?)'y?? \???VAʂ????~?`47?????;?b?4c?W`T?M /m퐢?Q?dl'iC??????/ޑ?+??    ?{'?+6??????$?U?.Dj?bt?C??
wi?z&?ϝ|?6?	????w?\?z??X??o?? /???!1Dx?6?D+аt??^&6??\p? ???r@6?Y?<???l?????Z???\Xe|a}w???z@IB?J??Z?̆??????????F?-??CKJȣM_]wd .?+]??,3?/??N??] M@?-?/?w]?ǯ????uT^Xf,?6??ܸS??n?n38?P??{ "?j?9΄ψ???YKO|?r?j????P?????&RK?q?Y?A???g?-???~B??}5h;???A?rWD?????h??8y?6?aT??/)
g?zv`?uz??$e??k~ah?8?v??Y?? ?Y$?\?{+???????kx?@?ӷ?Y?ߊd^n98?n,?<????^0?4d??*v???O?8@Ak]??(fzY???֣??????x?7?ž?-????6???//???k????ɂu?? 2?!??}??X۫?N8?K<??`)Y??f)3??b?Ǻ??3??R????O?NH?%??>8TuWv?????9A??]?2???1??Uyj)??.r^???(wX?p -&?	6U:OX?7?n??[SXG?+7?W????F????X?????$JK??????(wȊ?P?[??j?a???psy?.g2??u??/???.us?u??]??m֥A^X??W?dm?G Kj?j?}? Vq,EqO???Ba??x?cBS??Uvk?~??kI??????????Sh)?h:??@2????˅?C.>???@r??Q0	!?鵤??YA3vn??+???????????Jw?B W?ސ?????????u?曳?R?[K>??s??<?^???\!z}a}??X??b~??P??X?????3???s]t?7M\/E嬮?S??'?;???~=/??Q??=Hr??)?+?$??A:K{?n??*@???3<??KSN:H^?0?g?????nۀ?w>??;????.?ǅ?\
??@-??`?q???#??G?Cf	T??u?l80??XY~c?:?{a?D?J????Ϥ|???w??ҧ??G?c????:????kV?\??Z?WP?????y???????Z????U??5???S9?.b3?J?????F?{"??V?.??d?^ф5?Y ?^???!=??2?'D]?u????f?1%?????^.?`??u?7f?
ۃ????,???!\???]<xxa???Z?g?u?? ^?"?????i't??t? ?@??J?-?I3?em???X'G?????#?UռN?c?rk?XE?6???tA?Wc5ȀɃ??A-!l*?6=4㽵_Y?G???60S5l????W??5'?s????s?Fw'?]?W?).K?q?\?4?4J????T?9?7??5T?!??Rw?X$?)G5???̛VɼUf?>t
st??卅?T??w??`???+??	S?0?????vǳ E{?D??:`p???*y???&??:?UӾ?????P?I?`q<?Ԃ?????$??)Ʊ;'8e?|?d?V?c???AYw??"???<?No?5ԯ?\??t!Q??*??⅀Y????????֣ԡ?j?y??؄EDƉ?k[ ???????N2?A'??.?TQ????"Bw?J??????<??Q???J?ɥ9??P?X]x??Uy???S4?${b????fp?,??m?$?c{??b??\?πJ??\A???g_??BX??P!_)?7??(aM?)?
B??Z?n,A췧????/7gXE?/??S??ʱ???6??+???w???UVO?TiZAD?*4??P??⭍??u?Q??^=???RD?nR쮻!T`S?~7W9???G?p?I&ՠ???aC6?]a?&?鷛A9????j?ʓP???>??? 6t2%????Ҫ??)x/?rc,?0B?[᎑ ????|]??a%?93$?劽?h?)?a?] ?Ml?{#??!!??F??[??ÛAwŕ{U0x????:"?Lа#B~ɳ??"8N??s;n!?\????(e"=*????e?X?g?&WEXT??(??;?Q?0C?ϔ'Ox???(?? քM?6??????????v?O??????NV???7k???%ᵮ??+c5?ٍ???q'/??-?v???2??=p?}????Y??=,q??j<?????a?K??ZM??8?B?q່^??-?"r?/???	/f??????=?Ax??)?=<?,??;0x8??r?D??+?? ??f????#L኏??r?0Į]	*?z'W?/??E??^?>???cx>???&L?;???9?-??Ǎ?Ú?z?'?? ??I?v??*???x???^?%?+K[7?n?##>&ETN??&2?E?ɕ??ˡ????
?f?b?.vJ?q3???/?M2??ZC???Y??Q?[??0??g`??uÏ4h9Jvv
????,??????΂J?????i??'?	?wԩR~??J4?-??E:;km,PP?????Zc?줦T
Vy+?[c7F???3?????''?©?wX?cc?(X?X? w?i;????^P???:8_\?C??-v6??F?n????~??t?0?I,?v?W??fW<?m?^??tꖘo}A}??B݋Sa????6?i+-?8o(?;\?????
!??TsQ̌}?yl??c?;??%?;??(W?g?@?TA?2?魆?Tm{??{W?=??I?p? 
? ???
?P??W0?'w?Cػw%?????w???#ū?N9W4?pe?m?Y?ʏ??(W? ?ڂx`j?7?????V?p??\?]xa}??u??ȕm?d?b?????7????|?L#O???#K５l???@Q???Y@?4???oR?s???ط?Hc?}??ٶ??????htȬQ?d??YpxO?l??;y?с??????,g??<,???-W<??
?p!?V2?d{E???'?B?STm?5H?PI/?4\΅NX????:`?%,{8??$l??mp]??R??P?q)?e???=t???Z??H?Q???qw?	?????????ޏ+??F??yz??a????P`???:?????O???l?!???Z??6y+?&At???SQ+(?꒧&??]?vQȣ?A9J?`0(??T%??ļ0K????,?0?/??4bn?̳?
?_??????~?y7?޵???rVg????ٰx???h??ksl?n?˫??B:b??^`[?݆]/?8?la?0?.~?Ov?,tF?#?L??-n??????o(?{?_?egE??\T I?W???'?IaO(@?F??????ώ??kMH?	??Z?*???M???Ǿ?g?.??xadj<t/ǳp~??#?"???v???Gɵ&GG??"<2?`3/OK5??e>_>???3????????5^ y(??	?^??)+?wu?????????;???g?ž?.y???/?u`?l^TG?ke?ԣd??Vt?7???嗀a??? :a????8?D??r?O㈕????(WX?h+ᴟ]???˳r?S?5э?????\?Iya??Yk?????z?G???#ww?d,?/??sq?\?@?Y?̹??+?@?;٥??2??????i?1?_b???Ã|?X???'{m??ӊl??x?4?????+?	?rcq&N~@Px?????*-?:??KC???z;'卵????bF$?i?b9??AbbO??g?A?????2???$?W????94??w??zۻ??????B??Z?????pL6|x~????;?;w?ǥ$;K" K?R<'L)Te??;k?wRX????x?(??M?Vc5??ބ	X??9?n??</2??	u2,??f?~?$???? 2??c&?X;s"/??o+?^??A?E?
K?ڦ?tHV??Ȁ?s??Gx????	Nr??Xs???KlS?????i_H??F?m!?A_4x??	k??oJ??????d	?u?85?\?|???,?o?-<??"vV|AԶNc!x?!l?==Ǹ??7T?p    ??@r?'	o??E?v?5Y?*sO??2???B?^X??UL?wL?:qxr1?#zՌ?t'??ㄟ?|?$Q?,?5PB??t?%??.q???!?????9??v??x?h??
Š?{???f?fc;v?T?I?pN??Y?? ?`??Pv?2????̮??ֵ?4!^
??&??g?)? >9d???=??? :IB??e_[??;V?ɭ??.??[?E?y4????.L?*??g[\?ձ?(?o???	}H?2?;?N??????Э?T??P????????????]ki?9?8?	bƀ?ŌhA0?[?2?.#?Q?????Z??q?~?RB????œ /???ߒ?k???E7W??e??{??$?4???B????U??L=b+???]?=?$>???(M?Z?Xxj???ˠ??3???????5??g??	y]?Ja??<??????B5??,v??/????e?";?z??+?d?*O?#??????:?}??^VK???TX^?1TWe?V?J~r??4a[??A/6ŉ?C'???b??^?q;J?@? ?K? ??`?aF?c??~]%B ????~ȵ@?}?r.?*.d?kP?J??m??e?G\c?XCo%?Ǐ??}&?U????Xv?c????a.???
?^m?λ?6a?c???v??%?;???ؕ1d?m?0
;?͚?)??wN??????????????~e27??NA?b?$???P0?OXX'?og?4|?????e??J-x7??P~aD{đ ?)\LF?\~??H??m?#^l???:???k4m[|?zo?I????1DN.???~??*W|= ??~?}x??׾????e?!?X'yx8??ؕ??
-????l??d6tC?U?7?;\?_??Py?@xYp??&?v?/????4????1??=2?????c[?y?vh?R	/??׵K?q((?rc??????&`	?)??b?N?q3??!????1?.-!P??rG,_X?)/????8??PK?ɺ??????]-[?<??|????V ?]?Q??Ş4???W??????m??G_&??<¦??u?]?9x????Bb?yrk?_Ħ??ՅD??#?bm???#k4~?i?X??a%???3?d\?q???'??M????b??2????ģ'pØ????n6膉/???+h????&\l? ???R??K??i?'s?W?M??8?eSd?S?Jϱ]?8?3?^X?ò2?0Y??1?i7?V(???׾?ų]?cYGՅ?J%-???Gi??,;l??nϻkw?+/??^딋=?9??$?z??8??{T?Ǹa???m?V???Su???d77xa׼?ټ$0Q?????6@r??2?DL?{R?DIx?e?X?W?$o\]a?
^E?my0?X ?F??"H??\?	?/??28q?n?md?a?$??U2@x???ġ??fb???B??	?띲??Ʋ|??{?7??jPvG???$6kg'???O??H??ў?:j~?Us???2?[?\?e?	??O??i?O?{4'9!b?;-???M.Aެ??????g?i?Y??d]M?A?"?aS??C?^?_?c?޴'????:??1h"?cw?iY??G??ӟ??%???????Q?????sZ^s?~\??7?]TJ?=?B???gKnM?b?1T???Ӷ[??_N?U}bp?
??>?)??g?f????vP???;?????8??8C?g??????3L S?$q???z??:? ????? ?JA?%N?Ll???Ŏ?j???$k?j????????7?<??g?ĝ?ϞG(?xR\??U'Ӹx7?[?h\yɅs]7V??nH/???*?_9??Y??J??z?P?k??b	?U4>v?Iְ??G???? ?!H?'???g?v3M}a}? ???Z???Rx*U?????Y??{4D??	pLܘ??C#??CX??+?R?gh0?d??u??????Ve'???#O??ݿ *??g{ã1? ?V〇WG?^?B2??}??u?d???g?.?-V?Z5{h,v???Nif*海N҆?l?I??u)l^1{?fv>K??????????`R?&8uN????ߵB??Y??}<+4????kc???B?????zJp??Jl????%^?~????0tŊNV???V=?????????
bW???:???za?1ܒR?nb+???q????ߛq7??uE???G8?Y????1`?}??????~???JJ????L\????d??ejc?5?e??o?Ƌ????2???????3?9??	?{@???lo?Ϝ?	zq?=/5|خ???y???u?\ܢ3:*'?x+???GRջ?????ur%??4???);???wH??{j??npd??Twc}^???+wo?N|jL"?l??Q????:?HV????A/'??P??-?ޜ??8o?o????ހ??mv6?g.??Q???(/9?ϒ??M-}.??58??yNϦ+????8`Eya}???woHx$?wغfԝ???O>-??Ȼ2#???_N???L?IF?k??b󶌓?C?YWE?
mA?sě???'n(????O* r???????t??r??fe?K?Baq?=?x?(g?&8?ҭ9ީ??-O??B?i?m??~?^???????&Ak???hG?>||?`vK<﫽?:3??΂f????O;?e?x?R?n?!>???=??I??i? 
	"??Xê?D?0?	??]V????K"??䔥??b,e??o,c<y ???+?ȹ??$d^??&ƌ@???gWO?q?1L"{@#D7w???p??:????@????#?I?0???p????[eldu?_?n].go???ƛ'%????ȵ????i?}? s???zb??s?U?U,?A?NXE=??n?B??G]|A?4?l?$o?????:??e??N
A??kw???:?|????ã4?pN?[?;kA?\6??Z???????E(?<?y)??6??	N?ΫIdC?l?e?????G?UNY????_?4r?F???.????o?M0@?f??5??wK? ?/OxҼ0?Cf#??l???o??ü??	?3?????m?γN??p?s?=?w?c???X?3?@6?X's???????d???7M	?n?,l}-?^X߉?g??2;??A?뻜6?8|\?[?:??:??"??G??? }?u??B?????*kz???$g<?J輛d???JzZ?I?d??`ꔭu-?L??g/ԣ?e%?'??;مQ??BM?Ә???4?L-??>???md?ꂸK`2ҝ??y?ٰ??????h?}A}/?+???3w?[[?????1????)??)??f?????vx????>U??,?ȿ???????k/"??b???!?Z
??w??(u???<?p????=u???7!l??yc}?ZB??5?=??h?D$!??	??/?dN'?¸4"????ê?`??Z????C.?/??P,???X1NQ?J֮??5rH?>?v??h?(Oo??k???c???5B??????:???.J ?zb9?? ???Z??E??T?^՞O??Q?Х????k!Sue?D#p???9I????o	GiB2l??kG?F5?b,а??ֆRVM???Nzp??F?q?ŲQ???	jX?n?RJ??e?\???1?5rFr?g?`?5R??È%??ќ?^0??:???Li??Xϸ??~?c8J??????????? ?:dJ.c_I?ߠ?w?Ia?+0h9?;Sq5??%!?mҴ?Ixc}/???_???vĢ?/"?m,???B????.?=??5;1Q??ku??n?`?5???I??Z?
?υ??m?N3??GY??8d҅纎?*??J??L?.?;?6?ݓ???W???wc??,8sce?,hu?0?O??s,???{??u2?$??AF??G???????f?a?89?X??uw#?W<?\< Y?ucy?LO?8???SKs6NcƖ??Gui_? ͹w?>Ms^_Xm&S?iB?nj?74??!?vWz`??M?{	!??d???朇    I ???=?[????????rI??MWR?????P<?/???\`W֧e	?X????Y%7v?.?6XC????48_??P??ӌ	?~-8El?U ??t?8???*???;??X'??3;??QLtW[yE?7Wn?`}?i~a}??2????wr??I??????7?樘?{??X'?B?wA?3???????Ŕ??9????p?&??ѳ:?a?	_|͈?軵o??????N8????~?ӳG2?jK'??r?iB?FN????r?om??/a?L?R???E??X22e	?k????%? o?qz?b{?)???Y?B?????U0??|?p"?????????{??i@?u??pΗ????9??m???4?ދ??F#?a????g??????y?A?X?n?O{???,?Vؽ??%???[?V?1mn????+???$I???A?D<^V?bq???4??f'?䔟?:??ln8|Ha?{)?u???k??X^?XT_X??T@?h???.Q???>?}?J??,?x|?$????????I???*??*????????S ?Gέ?y7";?i???M?-??׍u??>?h??u?>?%*???"4I?۪;?^?j=???כϤ>?X???Ny??uY?+??W?%??@??j=??-?&???صk߾K.?"?????Wx??9?,x'?z?4??B??X??ϷuԲ?(H??Īq???+!&??S??[y?oA?????i$o?J?@>??ޞس???0??Da??|???1?3??8?t?y?#??e?oW??;w?? ?i?Y?Қ?l\b??k?P?:????X'$?{?TM??7?y??QF?m??Ϝd8J???w?????A????z??Ɔ*?b?????Y?B??
g????J@??E^䪹?N??%?7[?R?l??r??D??ZϷ??	??Þ~????b?????߇R+]?N?!?Z?e???D<??
?}?"?s?]???ewКzc????\!?m?û????z????GN?<|I/??&5?k?9@?cQ?͌R?R´?? ?J???4???B???i9?
V?}?~O???a??ƣd!>?秼S?,?h??(J????q?
?u4?}̀??r*?yM??e??;?c??Qkϲ?	z?5P???????-?????urп?M?
?H?????*B??4?~Y?	??w??rK9[?Ў?X??֞{??a?2??^?:i<>?cMa𦑶8t?7?:??>?L??EM/??6?Vxo{????	?Q?z?v?W???&<?<+?2?협C????&???톲??3????6~?y>??"Dv??@B?D??????<??i?%Љ?????\???u?b^w??i?x?(I?P8(?PA2Vm?~???~???+??? ??????? ?s?j??XCY?Wjꍅg^H'?%?=???[t?iJ??{ܷg?7?H?0??4aBg?t?s@:????Gk??j??㗥??:?]??އיj*?B?#P?uc٥??U?(O?b?e???b??=08?P???Mu皺?{:??ҺyK?B ???r ????z7bɣ3?	????)?????+$,??B?5?P 4?_P?My?ӎ?U?o?g?7???2b??cc?j?q????cM????????7K?.8???:???f???8L&?}?????H݅??-\~X??b$??;/?1z??[Y??83vC%?zA4?b???^?	?????????]?????_?~ҵ??m {?X?!g]?????qcQm???:???lL_??sY?RZ?W?C??	?IZy?ܓ$???1'/???4?6$?ن???F??^Pߵ|?qOM??????Ԋ???(6????4-Tȝ
?۔m????0V?6c?ro??n?L?Ai{e?:q?;?k1!B?+)???=m??b?/b???0	)YZ????P*W?Gx2C??P???(9)?
w?i?򾉙?v?y@?uF?=̙??????C%???=$3?}?Y_X??"?Ȉ?O(?????$])??%??????G,>I???I8?\|f?iR?tV??w??z?Xl??u?N×?l?,1??5! ??w??2[t=?u@1??l?-?Ķ)9?l#??]?K[Xx???u,d??3?ƲXl5?Xu8?NW????^??h?I??"???ټ*rN?4?G?X?M1??/????z?V4?3`?u?7a?U?č???[N?(K??j?%*????oK??So??sO?{????&??g???????x???"??u{?T«?N?.?ƙ????$?dX
?an3Dͻ<(e???xA}\???'?J;I?@c{tp?{Y !?Yx`???@???]<Gqp???-Oْ??&?????63?U??Q???[lx?k?o?,,Sy`?tu9-???BD?F?ʕ????#??m??vr6?^?t`Y?ks??Mf?B/??'?a]??:?[<턁?????j!??耦$??= ;?????!H?wV??:??????'??v???a??@???"??(?T-(Lp??F?Cc/??)??ދ~Jc	?1?)???*cOlI??==??Q???TP??&???n)b?JCv,?}????{F?HY??-?hv?aF=?c?w?9>?Nn?ƪ??'WM??BɃ][??(3??*#????`?$??h?!d4???#9?~wH?el???N<?T?A???E6v??C?7?????:H}?ͣw14?ى?? o[zC8??c??+??<?r??S8{`?T?WW??N8?wC?\????o??X?R?[r?ŭ????.J??R~2B9????K??rI?I????'?h??jB?????OR??|??虫???Je`???wU?? ?~?Nr??T??
???X?S?+?s??up????ˢ?W?c?G6?,?S?-/l7B????0???8V?0fd??+??!oX?*a?+M??˻????/??*ʂ????C?,??<??2B(?z ?z???W?E0??5?ԡ??M????o???ņܳ)?:\??Y?a4??:?W?P?=??I?pe^?̥???Y?#?tE<G??!O^?X???X?2?~x?a????W??*?=;h?GK9J??M-~H?3+|???Sf??;a??V)/??d.?T7?3??op??[?K???*
????'?AkC9?f*?ރ)I??????6?v?$???/_?P??????I??&?w2??RP?'?<I?????l??
Ӄ:???}l(+Wr9???-?"??Nϔ?ש-?+??ބ?=??.?????1b??8
?
+?$?TS?p?ԉ?~W|)??ao?PX7???f???˫??vO??.>?g?:`BU?'?????궆?)[?u??Ry6]??a??:[?~X! ?UZ???.???????y???p懯.M???mwPw?ϣ???P??A>_P?K? @?)O?x??/?C?|?t?<kJO?qTF???y5??ɑ ??WI?~?A??ٗ??????-???]:B??kK?2/v??X???$i'B?8^L???3??BL?*g???5???{B?F?$?}?Ϣ vm+k??8?2G?G;I?^n??}`gU֗tk??V?Z?Plu?R|aL?H?U????ؠ?]?ɪ?>???!??A0r??U@????hP^?%۾U??|^???$=?L???f?lY?y??r??|???A?/g??)e?o??\R???o?PZ?,?P?Y???zCԾ???,?R9x?<jKcnw?w??HG?A?C?*&e?!??%^%{d????f?/???X=?*sޜ??:?W`?0??????\X??	l??d????A-?y?R?U????7????7???B??Y`ulS?6C<????L??f6%???|Ci???#?@????l~C??e9ya?OƎ!??,?H?-???[??B?~??HG?A??$ ??I\I[q;??t?.^EK?uPv?f?,???l#???@u?	Yx?P'??p˱f?3t??`?S?Z??JL嚤???S??x?S?&O!9???Ҁ?,???P?=?	?)?'s7???	??G    ???&?*? /??|,???Y0?}?{?RϬ??{+???뤆????K?p[?dӊ?z?t?:?v?p?ϫ???%a??-gAs?4c=&?Ո˸?}v??F-?}|rHS
m?/?????s??H???p?)??w??|?ͳ???m7]͠*3ޢܛ+X||>??#?ɍ?.?:bd?&????)9Q)???:??1??àO-T??x?B$?V?١??????!#?R?A??(Xd??!$??
?"??>????? ???^??
???Wc??????`?v???$C{l?"=b_GUD??@?b?g?!????:Hep?????ن?~i??H?????C????h??JG?iv??Qz??[?,Gb%?}??I??-???l??M??
7lo???eWLṮ?1??"qg?p?^A??i-?6?7V?ފOu?'?-?6?WqD????*?L??ǳ ?`?uҔ?Q?.^?ݫ0?=???r;Ӛ3?冐_X?KX?39|???%{V??y'
s	?c?\:J?bo6^?B,
?EE??9r?7;????СU!??ք?-7?lw????ߢ?e?IՎn??e?<l?v?S?Gt??bs??8h??????B?e???0??"/???=`?`??8[?vq?3bg7q?z? ɨE} ??o7V??9}a??xlWމǶ`?:Р?m??+L3?	$<??I??:,D?/?əJT̽?????%T??v?u???m?Z ??{???C 0v????r/???y???0??+U?ׁ/??<?ߪ2?D^X?Y!|???]?f???zp#??݂O??1??ӿ??	y??bw??
rN??q??a??x??:)=?Lf?~??F/?Ł????s??<?v?nf?b??+???4?b?JS5?U?????DIKt??z?iq>3?????nO??????!\??????~?GLV]\?*=?K?Q?P?%???'?`t`7V?}s3?????*?C??Q???ؔ? JaK??U???c?͜8&???AgV???k??j???ac??x?+ ??/??Ĉ???m?1?g-???c???ꄮ?????N??.$7>?\??5?B:?)???)??3b^???? +?`#,=J???W(??H??Ǩl????aƀ,??	?݃?.??O;b??XS?s?',??O??????"?+?b#???e?u??9??Z???T??"Tc>?{?!??S?mL09XX?U?Z???,?ͣ
k$'??!b?F~?f+}?RHV??>x5/?s]?_?nݗ:??b?O??X?;V?ƌ/?L?????????	??#?%䁥߱?5D?R?6Jv?	???jcO{?@?????-4?֙"??????-N;`3?Ear?g ???S(?ǀ-?r??*????f?T????????V?mp"8??V?-i=?+-6?ۅ??r???????X߻??Ȑ?P????H??cI?>?????
??Qf?Zf??&??7?p.5?,N?????׈?%9#??????????S??V(?RU?#?N???zC"8????? i????>???XQ?<?U???#*0I????4?y?-{@????߁??b???]??b_um??[???u?2؅?C??`ռ1=??s?2?v?;{???-??g???̼??93/??[[?Y???????O3<pqN茨a?!?8?ai??7??`??????ۿX??k?;y?+???D?j?j??|???:彏?xNȆK?c?G?q?\u???} ???݆?9t????!????` 2???5?B?-g?<oӧ?ʃl??A?L?l?e?BIg???LAQSۆXXQ~?a9"??ݼY)??ce((<~oĺ+X?s{pcy`??L??????????=W????`~???B?`Cj?A R?{Ҋ%??Df6??`9?Dx`??/?B?g&?:@@H?#\c??w?=?|qB?s]n~%Ӆ?qP4>t????Q? QdT??~ǰ???#ȧ????]????+Cs?J?Jz???fxB?wg%6 ?n???yĥ?'B???{?|[n"?/?R?????I
ʮz"??Y???:??????s?V??ڊH6!????;9?|????:??U??i??:?髀K)?? ??[??M=?? uB#$w?v?/???G??#??2???????	5?01?:xD"s???w??;?n????zX?	3l??t?<Zͫ??/'m???	?????O?a?1?6Ǆ]J`6x??????~??/???????
r+?G??BB?m?8?s?>?~?	5q捇?Or?*o???8??.????????pC??l??]ClL?u??[???-?i?e_??9u'ց?Hُ\f??`H?@0Y??ָ?F??????u?4¬?}\n?]????~)?o)????=S?=?\F??z?}Ι???z?gzKq???F???f̏?uB{ó?????4?a?^?ֱ̽?dkzl?b??j?Я5A???Q???Ά?G?(?<??	1T???1?F?֡M:hrߍ?7y`,屻N?!G???&*?eZa??B???+?˅_?eG?0?7q????@Sx??;??}Ҕ???L???mA?-?*L?f1XL?N??s|??&$h?o<?3V???=Ԫh??9(?p???Vre??vD
m?7?CTv฽?a??>?T??d4??"ԁ{?F??ey?a??3????]??幬?l??
?ɩ?4?c?E?\???>???1?X>?^^?	??j??x4?ݫ?;?*l?W??8x0O?E??????C??v??}?P????ט???[??ʥ?	f???	(??U8x?90?>?K?0??j??R|???k ???? @?pBg??c?? 4?sX?Fh/?;̀?????:??f?Y?%???v?MjX!??.KSػ??F??1@??RN?o?5??	?Ƚ*D-~Јu?-?sʣLڞ4?R??qgs??h??c?0?ʔP?qwa?????;?m;v?Ce)?o??F??????Ee????C?U1?????NaXe?ۇB??|E?8BR?4???????(W?c?W?k?SVxg??G@w?h?:0??\Ձ????q?Uq&??ٌ,??????乇܅.З6?]?"???{???)??Ǉ&?~?? ?,O8!?:Bd?]jS9??????^U???<???{??z??#?V?+.j???{? 6????'t????8??	5?"??%??7?1?ix?4~?????xv0?V:?ӄa5???D?}? ????{%???_>B?x?W?ge???wl!I????????uAL????)5d?&??ޛ????{?#>??yy`??;??;L?}??f~w1??U;H?Al?bY???s???D*?hp???u??[?$ODa???=kJl???|?e?8????	?w(?????Ai???2x?????????:??????"?Z??????U?y7??;?????????a?.Xi?
o?O??x[%?7V^??ց?? ???;??l;R6?b?c>^??/?%???ff?????*?%??~SЙ?Wף?^F].??ʻ>즸b=?x$?Y*m?(?0( IB??g???x????Jv[j?8rĳ?
@?E	? ???\?P????=aB??%4?Y????v? k?x??c??XOY? ?6?)D????/?I??N?׼ǯD%???(\?	0lU???z?W?yl8?wk??A?hg?#??	Nʿ??yՎԄ?۱yQ????ر?Si????Aa???;@Qu-?P???{????$?Ed?p???ј1"????L??ف<: ????k??ǚ????6?????⑚1??B???w7????%???p????A9??I???L=?V?Z??? ?WʷF?{?4#?N?ɀXCK xW?????v,? 0?+? Cdt?m4??_?r?AYj?>?b??\P?枽ղ@D@?Jki?	p?[{?y??*!ܱ??r??ΆDE l?`@???}??|b??0D???%?*??rZ? ?Gv???C?H??=?.쁍c?<2 ???<???p?SK???$ J  ??a?bU???LӾ	?9&?Հĵ?Z????}p?P/MB?(_?	?AW:?0????G???_?^2??FQc-???ZPQ?a3svPtwL8?W.??d?4?:?(??"?O?\?X?????I?v?OȰ?"A ?%?Z`g??94??>lſb=d?????A??H?eN?97?F??i9?????Aa? sj????l#?Ԛ?i?ip??qp?c?\[H????i(.??
݆??y?,l?p?zH?BZ\g?&??!?.???\@???
۟?Z?/?pr?[u?4o4?o?<s?Nz$??b?]??e/!Ro??w?4??? 6??fZl/ޭ\?^Nq????P??o"?????!=???.??	"??Bj?1 y$??T_????A9?.??zH?=?dX??? ??9?#[m?)Ix?R?'`XӜ?Uɛ?r??e???J;ֲ?c????.????Cic????$=??z
J????߱^??nU?m?@M?????e?}???7/W?|????%?5?7R?e??{?j0;υ?????!o?(?ː??v????ON?1־|?TY??~?hv?rǥ???4٢d?}???x=???\{??????@@?q???G	4\? 9Z??!o?U<@@??l 7:?x?Ev??&?)?Lo?+??A??? h*?%?&A#?Yr?D?Bv?Z?H	???8,?F??PS?O?9???tt$}b??????ӝ?????? ??xr2@.???r?X???????{9?h??HOɖ?6?乒?w??4?ߓ???6%w???>"Mu??}q?(?agg????Z?@?Qh????Mȿʞ?RΡG??}+??<4T?	??S?z??3??׳.???B?x???<?|??x?[??6A???m?(Ĳ??Xy^?9W?Io[}?5:?Zp&???wHJ?MR??"?M???!?/ٸ?[??Օ
|H????(o? O?\?aP??????ܬ?ed?ЕԱc3???ʳ??#k@?m#Q??Rl?????j?7`k BZ??>8?La?<?ᶭ"%?)!}??d??I9Nھ???B?c?????ѡ??'???4
5)@?62?
G???*:??%?????)W1?w6l`??A5P?E|???????~?/?X??(4?S?2kd?s??G?ʾv?9???[vD???"??l?2?T;?4?b7?뜆???fp%?p?N??$@6 ?X?????@????}b??^YcKo???i?'???Q???:??b!A?_~????P&???e?>a?y??Pso?#:? 6Tn???Q??K?D??\?^?j?hF?!????W?}??%?v??TVP?Q$??f????????:??G?m?m?Ґ\???2B???γ?x?p?W?6??Q??Iu??U???Q?R?z?(s?P??]?e??b??h??۠?W??e???-'x^?q}?^cQ"C+{?]h?D/i f;GQ9???m??tr(<???Zê????*H???=?X???v}?^l?@w`9?u?]???'???'E??X/???1?!&?8[q??P+K?[wl????^? (ZQ{m?܁\??b??E?%???)?&??z?2??6???tC??\Z???L?ʻ????3???M`j,?????
???rbZp??????L??;?dq???a????8kxb%-???%?'?dq6???_%?E2<?g??Q(v??\??㼂?En?XW2?T"?U?GlF#??k?2??[???pR(o6=??????Vw?z?R??#?P0?^?ֱ&?00?ub?????!o?>;?|W?x???????y(;??9?µ???u?	?I+@p??(ǣQ?i9?????m???7?rܚ
?)*2F-??r?d?鼕?+?ݱ^??,1p>??4?EB???	V?c?H??K?C?h?:?QbВ?q??4???*4???w?2}B?[?,0r?-L%?Ns???sE@Q??f}?7tf<?(?w?a?v?7??2???v?a???8??@????"???PB3?ĉ?<~??|?Oe???c?1?L??:V?t??????:o?з?Leخ?V1???#??8ώ?r?t???d??)̳h?5??~??$?u}?9J]??????y??0Q?@ȱy+???G?????c,?%??c?>Pc???Q???=?u?>S8c?@+????=|/:?b]????-??qKR
*J??Žlzd????@?ASe*?H?]?s%?????F?3˗?>??lCA?h3e?4???G???????E?ft?*?cca??4???????z,???^?<!??<m????????	???m??'8???pIg̕^.?."a?Y?E?6??
_z?O??jP]?????+z~??!6??趟{?Zvt?-2??IQ???ı??d`{>??????޼??1f^??b?pD?c%Mr????I[?=͡p?:`???U<V?Vl?XR?????sV?m???6<Gk?Ө!???:-?:??X*??B?4?????9dH?`d?+?4?>??Zw%??E??5z?\[??k???҅???[?e,??R?p0?P+)=k??(:????2U??7ݯ?!kt?ZGY?'A??e??̈́?8??(]?8?????C??I2ߐ;́???}??V6???4?7?C?(???J?ġ?|? e?+q??]???:?<?*??<Eagչ?G?+?BK<?PNT??????9v??P?V??HgS?>??w?????>?5	NC????E3?^?V?6??v?Owʥ??P?8F?1d?<??c??{@YI????%c?쫲o???o???C瀙;sC"???k??2gH???t???2?'5y`I?b"????kq??8,? {w?Fe۵Im^???%?cqj#?K?????@?W?	k??#H??fL=???.??L???Ʃ?Fk???B)?&1?ds? ?(???|?S??R????x?X߅.?k?Z??X??(?W??Q?\f?m?I???6+??p:6}?PZ>????z?M?T??rO??2)??????i~?b??E??m? ??6V??̱?ѐu??rJd???ׄ?ߟ
???t?e??L?v?V*{???ce??{k??w^?q??5?ʁ?:?ۖ^?Ԕ@??~??_4?b?*?P?@n[u%8????nqk?]?^?>T/c?
??"~eE??V?sY??B]???J?ۅr?????"*:/¤Ž?????ߺ?^c?5??e̵???xd????}~?6\a???l%??????xvߙPr?U????4?GR???=??y.?6ʨ[&L???Wvz??)Sz!!!j???B]??_=??????_????B\?C???\c ?
X?>?}x?;m?;?_?KJ?H?Jq???a?F????V??X??)?????Y*x]S¦?Rl?E????u??~0||????X??tPz??(i?z??*g?~??????hӣ ?Bi?J?Vԭ?U?Y?PH? ????.?Dy??JG?@?kO=/?????_Ǡ?m?? 2cf׉??#5k	??(?ŐY??b?'XH/I?$E???F?:|?Ѭ??????c?'???ؿ1(???đ???@??+?r컞??VCT???N<??b??\{?9????P?????
?ϟ???ϟ? ??      ?   ?   x?U??n?0C??+?ɖ-?;?f?m	?!m$?ιI?8?|亾8E0]br$-]?W?:F6?)?/?~?????s{??a?OX`Zחks?\??6?????.Z????z?6??$?dkZ?d???÷_??8w?`ћ??X!a?V?PE+??Gg???h>???l$?[2o::?LQ>(qn&]?n??;??T? ?j?j????h??.?<???Us     