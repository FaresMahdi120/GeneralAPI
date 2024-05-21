package mc.thearcade.commons.utils;

import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;
import net.ess3.api.MaxMoneyException;

import java.math.BigDecimal;
import java.util.UUID;

public final class EconomyWrapper {

    private EconomyWrapper() {}

    public static boolean addBalance(UUID user, double number) {
        try {
            Economy.add(user, new BigDecimal(number));
            return true;
        } catch (MaxMoneyException e) {
            return false;
        } catch (UserDoesNotExistException e) {
            throw new RuntimeException(e);
        } catch (NoLoanPermittedException e) {
            return false;
        }
    }

    public static boolean removeBalance(UUID user, double number) {
        try {
            Economy.subtract(user, new BigDecimal(number));
            return true;
        } catch (MaxMoneyException e) {
            throw new RuntimeException(e);
        } catch (UserDoesNotExistException e) {
            throw new RuntimeException(e);
        } catch (NoLoanPermittedException e) {
            return false;
        }
    }

    public static double getBalance(UUID user) {
        try {
            return Economy.getMoneyExact(user).doubleValue();
        } catch (UserDoesNotExistException e) {
            throw new RuntimeException(e);
        }
    }

}
