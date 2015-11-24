
create  or replace  view epayment_view as
select pay.payer,               --收款人
       pay.idcard,              --身份证
       pay.chargedate,          --收费日期
       pay.bankcode,            --银行编号
       yh.dbdmc as bankname,    --银行名称
       pay.branchcode,          --银行网点编号
       wd.yhwdmc as branchname, --银行网点名称
       pay.payee,               --收款人（柜员编号）
       pay.bizcode,             --处罚决定书编号
       pay.revenue,             --收入（缴款金额）
       pay.banksn,              --电子支付日志号（银行流水号
       pay.tollcode,            --收费项目编码
       xm.sfxmmc as toollname,  --收费项目名称
       pay.unitcode,            --收费单位编码
       dw.sfdwmc as unitname,   --收费单位名称
       pay.biztype              --业务类型
  from EPAYMENT pay
  left join dbyhk yh on pay.bankcode = yh.yhdm
  left join yhwdk wd on pay.branchcode = wd.yhwdbh
  left join sfxmk xm on pay.tollcode = xm.sfxmbh
  left join sfdwxxk dw on pay.unitcode = dw.sfdwdm
where MAKEINVOICEMARK = 0