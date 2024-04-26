#include "task1.hpp"


std::vector<std::string> months_with_max_completed_sum_total(std::vector<Transaction>& transactions){
    std::vector<double> month_total;
    month_total.resize(12,0.0);
    for(Transaction& curr_transaction:transactions){
        if(curr_transaction.status=="COMPLETED"){
            month_total[curr_transaction.ordered_at.month-1]+=curr_transaction.total;
        }
    }
    double max_total=0.0;
    for(auto& curr:month_total){
        if(curr>max_total){
            max_total=curr;
        }
    }
    std::vector<std::string> result;
    for(int i=0;i<month_total.size();i++){
        if((month_total[i]<=max_total)&&(month_total[i]>=max_total)){
            result.push_back(MONTHS_NAMES[i]);
        }
    }
    return result;
}