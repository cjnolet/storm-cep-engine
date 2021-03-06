package org.calrissian.flowbox.model.builder;

import org.calrissian.flowbox.model.FilterOp;
import org.calrissian.flowbox.support.Criteria;

public class FilterBuilder extends AbstractOpBuilder {

    private FilterOp filterOp = new FilterOp();

    public FilterBuilder(StreamBuilder fob) {
        super(fob);
    }

    public FilterBuilder criteria(Criteria criteria) {
        filterOp.setCriteria(criteria);
        return this;
    }

    public StreamBuilder end() {

        if(filterOp.getCriteria() == null)
            throw new RuntimeException("Filter operator needs criteria to filter");

        getStreamBuilder().addFlowOp(filterOp);
        return getStreamBuilder();
    }
}
