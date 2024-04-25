#pragma once
#include <sstream>
#include <iostream>
#include <fstream>
#include <string>
#include <regex>
#include <vector>
#include "MyException.hpp"
#include "Datetime.hpp"
#include "Transaction.hpp"
static std::regex STATUS_REGEX = std::regex(R"(COMPLETED|CANCELED|CREATED|DELIVERY)");
static std::vector<std::string> MONTHS_NAMES{"january","february","march","april","may","june","july","august","september","october","november","december"};

std::stringstream input_from_file(std::string input_filename);
std::string scan_json_struct(std::stringstream& input, char start_char);
std::vector<Transaction> transactions_from_json(std::stringstream& input);
std::vector<std::string> months_with_max_completed_sum_total(std::vector<Transaction>& transactions);
std::stringstream months_to_json(std::vector<std::string>& months);
void output_to_file(std::string output_filename,std::stringstream& output);
