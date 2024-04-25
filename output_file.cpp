#include "task1.hpp"

void output_to_file(std::string output_filename,std::stringstream& output){
    std::ofstream file(output_filename);
    if(!file.is_open()){
        throw MyException(-70,"output_to_file: no such file");
    }
    file<<output.str()<<std::endl;
    file.close();
    return;
}