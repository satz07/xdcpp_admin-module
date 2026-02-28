CREATE TABLE "public"."payment_code_master" (
  "id" int8 NOT NULL,
  "publish" bool DEFAULT true,
  "payment_code" varchar(255) COLLATE "pg_catalog"."default",
  "payment_code_type" varchar(255) COLLATE "pg_catalog"."default",
  "create_at" timestamp(6),
  "create_by" varchar(255) COLLATE "pg_catalog"."default",
  "update_at" timestamp(6),
  "updated_by" varchar(255) COLLATE "pg_catalog"."default",
  CONSTRAINT payment_code_master_pkey PRIMARY KEY (id)
);


INSERT INTO "payment_code_master" (id, publish, payment_code, payment_code_type) VALUES (1, 't', 'OMT', 'PAYMENT'),
(2, 't', 'DCT', 'PAYMENT'),
(3, 't', 'PGT', 'PAYMENT'),
(4, 't', 'CCT', 'PAYMENT'),
(5, 't', 'PID', 'PAYMENT'),
(6, 't', 'OMT', 'PAYMENT'),
(7, 't', 'ACH', 'PAYMENT'),
(8, 't', 'CCT', 'PAYMENT'),
(9, 't', 'BAC', 'RECEIVE'),
(10, 't', 'CAS', 'RECEIVE'),
(11, 't', 'WAL', 'RECEIVE'),
(12, 't', 'MAT', 'RECEIVE'),
(13, 't', 'UPI', 'RECEIVE');
