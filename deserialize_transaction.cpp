#include "task1.hpp"
static std::regex DATE_REGEX = std::regex(R"(([0-9]{4})\-(1[0-2]|0[1-9])\-([0-3][0-9])T(2[0-3]|[0-1][0-9]):([0-5][0-9]):([0-5][0-9])(\.[0-9]+)?)");
static std::regex TOTAL_STRING_REGEX = std::regex(R"(\"total\":\"(0|([1-9][0-9]*)(\.[0-9]+)?)\")");
//if you change regex you must change code


static std::string ERROR_MESSAGE_WITH_CODE_40 = "scan_json_struct: no such start char";
static std::string ERROR_MESSAGE_WITH_CODE_41 = "scan_json_struct: Not valid json";
static std::string ERROR_MESSAGE_WITH_CODE_42 = "transactions_from_json: Not valid task json(42): not valid start char";
static std::string ERROR_MESSAGE_WITH_CODE_43 = "transactions_from_json: Not valid task json(43): not valid start char";
static std::string ERROR_MESSAGE_WITH_CODE_44 = "transactions_from_json: Not valid ordered_at syntaxis(44)";
static std::string ERROR_MESSAGE_WITH_CODE_45 = "transactions_from_json: Not valid ordered_at syntaxis(45)";
static std::string ERROR_MESSAGE_WITH_CODE_46 = "transactions_from_json: Not valid status";
static std::string ERROR_MESSAGE_WITH_CODE_47 = "transactions_from_json: Not valid uid syntaxis";
static std::string ERROR_MESSAGE_WITH_CODE_48 = "transactions_from_json: Not valid total syntaxis(48)";
static std::string ERROR_MESSAGE_WITH_CODE_49 = "transactions_from_json: Not valid total syntaxis(49)";
std::string scan_json_struct(std::stringstream& input, char start_char){
    char end_char='}';
    std::string result_group{start_char};
    switch(start_char){
        case '{':
            end_char='}';
            break;
        case '[':
            end_char=']';
            break;
        case '"':
            end_char='"';
            break;
        default:
            throw MyException(-40, ERROR_MESSAGE_WITH_CODE_40);
            break;
    }
    char curr_char;
    input>>curr_char;
    while((!input.eof())&&(curr_char!=end_char)){
            if((curr_char=='{')||(curr_char=='[')||(curr_char=='"')){
                result_group+=scan_json_struct(input,curr_char);
            }else{
                result_group+=curr_char;
            }
            input>>curr_char;
            
        
    }
    if(curr_char==end_char){
        result_group+=curr_char;
        return result_group;
    }else{
        throw MyException(-41, ERROR_MESSAGE_WITH_CODE_41);
    }

}

std::vector<Transaction> transactions_from_json(std::stringstream& input){
    std::smatch matcher;
    std::vector<std::string> structs;
    if(true){
        char skip_char;
        input>>skip_char;
        if(skip_char!='['){
            throw MyException(-42, ERROR_MESSAGE_WITH_CODE_42);
        }
    }
    try{
        char start_char;
        while(!input.eof()){
            input>>start_char;
            if(start_char!='{'){
                if(start_char==']'){
                    break;
                }
                if((start_char==',')||(start_char==' ')){
                    continue;
                }
                throw MyException(-43, ERROR_MESSAGE_WITH_CODE_43);
            }else{
                structs.push_back(scan_json_struct(input,start_char));
            }
        }
    }catch (MyException exception){
        exception.update_explain("transactions_from_json: ");
        throw exception;
    }
    std::vector<Transaction> transactions;
    for(auto& transaction_struct:structs){
        Datetime date;
        if(!std::regex_search(transaction_struct,matcher,DATE_REGEX)){
            throw MyException(-44,ERROR_MESSAGE_WITH_CODE_44);
        }else{
            if(matcher.size()<7){
                throw MyException(-45,ERROR_MESSAGE_WITH_CODE_45);
            }
            unsigned short year = std::stoi(matcher[1]);
            unsigned short month = std::stoi(matcher[2]);
            unsigned short day = std::stoi(matcher[3]);
            unsigned short hour = std::stoi(matcher[4]);
            unsigned short minute = std::stoi(matcher[5]);
            double second;
            if(matcher.size()==7){
                second = std::stod(matcher[6]);
            }
            else{
                std::string sec = matcher[6];
                std::string millisec = matcher[7];
                second = std::stod(sec+millisec);
            }
            try{
                date = Datetime(year,month,day,hour,minute,second);
            }
            catch(MyException exception){
                exception.update_explain("transactions_from_json: ");
                throw exception;
            }
        }
        std::string status="";
        if(!std::regex_search(transaction_struct,matcher,STATUS_REGEX)){
            throw MyException(-46,ERROR_MESSAGE_WITH_CODE_46);
        }else{
            status=matcher[0];
        }
        std::string uid="";
        if(!std::regex_search(transaction_struct,matcher,UID_REGEX)){
            throw MyException(-47,ERROR_MESSAGE_WITH_CODE_47);
        }else{
            uid=matcher[0];
        }
        double total=0.0;
        if(!std::regex_search(transaction_struct,matcher,TOTAL_STRING_REGEX)){
            throw MyException(-48,ERROR_MESSAGE_WITH_CODE_48);
        }else{
            if(matcher.size()<2){
                throw MyException(-49,ERROR_MESSAGE_WITH_CODE_49);
            }
            total=std::stod(matcher[1]);
        }
        Transaction curr_transaction;
        try{
            curr_transaction = Transaction(uid,status,total,date);
            transactions.push_back(curr_transaction);
        }
        catch(MyException exception){
            exception.update_explain("transactions_from_json: ");
            throw exception;
        }
    }
    return transactions;
}
