-- select case when exists (select 1 from ws_entitlement_data_1 where contract_index = c.id and contract_coordinator_index = u.poetic_id) then 'CM' when exists (select 1 from ws_entitlement_data_1 where contract_index = c.id and          coordinator_index = u.poetic_id) then 'SC' when cpu.pk is null then null else 'EU' end "_"
-- select (select distinct p.email_addr from ws_auth_user u join ws_auth_user_profile p on p.user_id = u.id join ws_entitlement_data_1 e on e.coordinator_index = u.poetic_id where e.contract_index = c.id) as admin_email_addr
-- select (select count(1) from nu_contract_product_users cpu where cpu.cp_id = cp.pk) occupied_seats
-- select cpu.pk cpu_id, cpu.cp_id, cpu.user_id, u.enterprise_user_id guid, u.poetic_id sbl_id
-- select p.email_addr
select c.pk, c.id contract_index, cp.product_line_code, cp.license_model, cp.deployment_cd,cp.feature_type,cp.col_par_cp_id, cp.pk as cp_id
-- select (select sum(e.number_of_seats) from ws_entitlement_data_1 e where ((cp.col_par_cp_id is null) and (e.col_par_asset_number is null) or exists (select 1 from nu_contract_product_asset_rel rel where rel.asset_number = e.col_par_asset_number and rel.cp_id = cp.col_par_cp_id)) and cp.contract_id=c.pk and c.id=e.contract_index and cp.product_line_code=e.product_line_code and nvl(cp.license_model, 'NULL')=nvl(e.license_model, 'NULL') and nvl(cp.deployment_cd, 'NULL')=nvl(e.deployment_cd, 'NULL') and nvl(cp.feature_type, 'NULL')=nvl(e.feature_type, 'NULL') and e.end_date>=sysdate) total_seats
-- select '---' separator , ( select listagg(service_name, ', ') within group (order by service_name) from nu_contract_product_user_expt where cpu_id = cpu.pk group by cpu_id ) disabled_services
-- select '---' separator , ( select listagg(product_line_code, ', ') within group (order by cp.col_par_cp_id desc) from nu_contract_product where contract_id = c.pk group by contract_id ) products
from ws_contract c
left join nu_contract_product       cp  on cp.contract_id = c.pk
left join nu_contract_product_users cpu on cpu.cp_id = cp.pk
full outer join ws_auth_user        u   on u.id = cpu.user_id
left join ws_auth_user_profile      p   on p.user_id = u.id
--where u.enterprise_user_id = '201610262144378'
--where u.poetic_id = '66414291'
--where u.id is null and cp.product_line_code = 'AECCOL'
--where p.email_addr like 'connor%'
--where u.id is null and cp.col_par_cp_id is not null and c.start_date > sysdate - 20
where c.id = '110350007795'
--where c.id in ( select distinct contract_index from ws_entitlement_data_1 where coordinator_index = '66027010')
order by 1, u.enterprise_user_id, c.id, cp.feature_type desc, cp.col_par_cp_id asc, ascii(cp.product_line_code)
;
