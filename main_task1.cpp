#include "task1.hpp"

void task1_all(std::string input_filename,std::string output_filename=""){
    std::stringstream input_ss=input_from_file(input_filename);
    std::vector<Transaction> input_data = transactions_from_json(input_ss);
    std::vector<std::string> result_data = months_with_max_completed_sum_total(input_data);
    std::stringstream output_ss=months_to_json(result_data);
    if(output_filename.size()<2){
        std::cout<<output_ss.str()<<std::endl;
    }else{
        output_to_file(output_filename,output_ss);
    }
    return;
}

int main(){
    std::string input_filename = "input.json";
    std::string output_filename = "";
    //output_filename = "result.json";
    try{
        task1_all(input_filename,output_filename);
    }catch(MyException exception){
        std::cerr<<exception;
    }
    return 0;
}
