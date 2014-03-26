package de.holisticon.util.tracee.contextlogger.utility;

import java.util.Comparator;

/**
 * Created by TGI on 22.03.14.
 */
public class PassedContextDataElementWrapperComparator implements Comparator<PassedContextDataElementWrapper> {

    @Override
    public int compare(PassedContextDataElementWrapper o1, PassedContextDataElementWrapper o2) {
        if (o1 == null && o2 == null) {
            return 0;
        } else if (o1 == null && o2 != null) {
            return -1;
        } else if (o1 != null && o2 == null) {
            return 1;
        } else {

            if (o1.getOrder() == null && o2.getOrder() == null) {

                // must compare names
                return compareNames(o1.getNameObjectValuePair().getName(),o2.getNameObjectValuePair().getName());

            } else if (o1.getOrder() == null && o2.getOrder() != null) {
                return 1;
            } else if (o1.getOrder() != null && o2.getOrder() == null) {
                return -1;
            } else {

                int orderCompareResult = Integer.compare(o1.getOrder(), o2.getOrder());
                if (orderCompareResult == 0) {
                    // must compare names
                    return compareNames(o1.getNameObjectValuePair().getName(),o2.getNameObjectValuePair().getName());
                } else {
                    return orderCompareResult;
                }
            }


        }


    }

    public int compareNames  (String s1, String s2) {

        if (s1 == null && s2 == null) {
            return 0;
        } else if (s1 == null && s2 != null) {
            return -1;
        } else if (s1 != null && s2 == null) {
            return 1;
        } else {
            return s1.compareTo(s2);
        }

    }

}
