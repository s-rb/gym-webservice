CREATE TABLE if not exists doc_templates
(
    doc_template_id      bigserial primary key,
    doc_template_name    varchar(250) not null,
    doc_template_created timestamptz default now(),
    doc_template_updated timestamptz,
    doc_template_code    varchar(250),
    doc_template_data    bytea        not null
);

create or replace function bytea_import(p_path text, p_result out bytea)
    language plpgsql as
$$
declare
    l_oid oid;
begin
    select lo_import(p_path) into l_oid;
    select lo_get(l_oid) INTO p_result;
    perform lo_unlink(l_oid);
end;
$$;