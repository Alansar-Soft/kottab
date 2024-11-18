package application;

import entities.documents.ReceiptVoucher;

public class ReceiptVoucherScreen extends AbsRVPVScreen<ReceiptVoucher>
{
    public ReceiptVoucherScreen()
    {
        construct();
    }

    @Override
    public String fetchScreenTitle()
    {
        return "receiptVoucher";
    }
}
