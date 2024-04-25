#include "task1.hpp"


std::stringstream months_to_json(std::vector<std::string>& months){
    std::string result_str;
    result_str="{\"months\": [";
    for(auto& month_name:months){
        result_str+="\""+month_name+"\", ";
    }
    result_str = result_str.substr(0, result_str.size()-2)+"]}";
    std::stringstream result(result_str);
    return result;
}