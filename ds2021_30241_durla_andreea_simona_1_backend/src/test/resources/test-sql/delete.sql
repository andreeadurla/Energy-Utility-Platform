SET FOREIGN_KEY_CHECKS = 0;

DELETE FROM end_user where id !='';
DELETE FROM client where id !='';

SET FOREIGN_KEY_CHECKS = 1;