#ifndef TRANSACTION_HPP
#define TRANSACTION_HPP
#include "Datetime.hpp"
#include <regex>
#include <vector>
static std::vector<std::string> VALID_STATUSES{"COMPLETED","CANCELED","CREATED","DELIVERY"};
static std::regex UID_REGEX = std::regex(R"([0-9a-f]{8}(\-[0-9a-f]{4}){3}\-[0-9a-f]{12})");

struct Transaction
{
    std::string user_id;
    std::string status;
    double total;
    Datetime ordered_at;
    Transaction():
    user_id(""),
    status(""),
    total(0.0),
    ordered_at(Datetime()){}
    Transaction(std::string _user_id, std::string _status,double _total, Datetime _ordered_at):
    user_id(_user_id),
    status(_status),
    total(_total),
    ordered_at(_ordered_at)
    {
        if(!this->is_valid()){
            throw MyException(-20,"Not valid Transaction");
        }
    }
    bool is_valid(){
        bool is_status_exist = false;
        for(const std::string& test_status:VALID_STATUSES){
            if(test_status==this->status){
                is_status_exist=true;
            }
        }
        //std::regex uid_regex = std::regex(R"(^[0-9a-f]{8}(-[0-9a-f]{4}){3}-[0-9a-f]{12}$)");
        std::smatch matcher;
        bool is_uid_valid = std::regex_match(this->user_id,matcher,UID_REGEX); //only all uid matched regex
        return is_uid_valid && is_status_exist && this->ordered_at.is_valid() && (this->total>0);
    }
};

#endif