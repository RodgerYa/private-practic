-- /pharmacy/online/yifeng/notify/order/pay
-- /pharmacy/online/yifeng/notify/order/info
-- /pharmacy/online/yifeng/notify/order/refund
-- /pharmacy/online/yifeng/notify/order/status

select headers, REPLACE(content, ',', '，') as content from pharmacy.partner_api_log
where uri = ''
and created between cast("2020-01-01" as datetime)  and cast("2020-06-30" as datetime)
and (content like '%XR2020040407102165%'
or content like '%XR2020041903104795%');