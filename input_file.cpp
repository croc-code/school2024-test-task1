#include "task1.hpp"

std::stringstream input_from_file(std::string input_filename){
    std::stringstream data;
    std::ifstream file(input_filename);
    if(!file.is_open()){
        throw MyException(-30,"input_from_json: no such file");
    }
    std::string buf="";
    while(!file.eof()){
        file>>buf;
        data<<buf<<std::endl;
    }
    /**/
    file.close();
    return data;
}