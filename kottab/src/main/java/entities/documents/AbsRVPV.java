package entities.documents;

import org.hibernate.annotations.Nationalized;
import utilities.*;

import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;

@MappedSuperclass
public abstract class AbsRVPV extends AnsarDocument
{
    private BigDecimal amount;
    private String remarks;

    public BigDecimal getAmount()
    {
        return amount;
    }

    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }

    @Nationalized
    public String getRemarks()
    {
        return remarks;
    }

    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
    }

    @Override
    public Result isValidForCommit(Result result)
    {
        ValidatorUtil.isEmptyRequired(getAmount(), "Amount is required", result);
        return result;
    }
}
