package v1ch12;

import java.util.Date;

/**
 * Created by qinbingbing on 1/11/17.
 */
public class DateInterval extends Pair<Date> {
    @Override
    public void setSecond(Date newValue) {
        if (newValue.compareTo(getFirst()) >= 0)
            super.setSecond(newValue);
    }

    @Override
    public Date getSecond() {
        return super.getSecond();
    }
}
