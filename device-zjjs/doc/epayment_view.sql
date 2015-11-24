
create  or replace  view epayment_view as
select pay.payer,               --�տ���
       pay.idcard,              --���֤
       pay.chargedate,          --�շ�����
       pay.bankcode,            --���б��
       yh.dbdmc as bankname,    --��������
       pay.branchcode,          --����������
       wd.yhwdmc as branchname, --������������
       pay.payee,               --�տ��ˣ���Ա��ţ�
       pay.bizcode,             --������������
       pay.revenue,             --���루�ɿ��
       pay.banksn,              --����֧����־�ţ�������ˮ��
       pay.tollcode,            --�շ���Ŀ����
       xm.sfxmmc as toollname,  --�շ���Ŀ����
       pay.unitcode,            --�շѵ�λ����
       dw.sfdwmc as unitname,   --�շѵ�λ����
       pay.biztype              --ҵ������
  from EPAYMENT pay
  left join dbyhk yh on pay.bankcode = yh.yhdm
  left join yhwdk wd on pay.branchcode = wd.yhwdbh
  left join sfxmk xm on pay.tollcode = xm.sfxmbh
  left join sfdwxxk dw on pay.unitcode = dw.sfdwdm
where MAKEINVOICEMARK = 0