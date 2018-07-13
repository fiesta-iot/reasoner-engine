package eu.fiestaiot.reasoner.service.service;

import eu.fiestaiot.reasoner.service.web.rest.vm.ReasoningStatistic;
import org.springframework.stereotype.Service;

@Service
public class StatisticService {
    public ReasoningStatistic getRegisterRuleStatistic() {
        ReasoningStatistic result = new ReasoningStatistic();

        return result;
    }

}
