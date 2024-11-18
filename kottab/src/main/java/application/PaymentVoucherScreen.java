package application;

import entities.documents.PaymentVoucher;

public class PaymentVoucherScreen extends AbsRVPVScreen<PaymentVoucher>
{
    public PaymentVoucherScreen()
    {
        construct();
    }

    @Override
    public String fetchScreenTitle()
    {
        return "paymentVoucher";
    }
}
