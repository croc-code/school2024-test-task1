#include "task1.hpp"

std::string getContent(const std::string &filename) {
    std::ifstream file(filename);
    if (!file.is_open()) {
        std::cerr << "Failed to open input file." << std::endl;
        exit(1);
    }

    std::string line;
    std::string jsonContent;
    while (std::getline(file, line)) {
        jsonContent += line;
    }

    return jsonContent;
}

std::vector<Order> parseJson(const std::string &jsonContent) {
    std::vector<Order> orders;
    size_t pos = 0;
    while ((pos = jsonContent.find("{", pos)) != std::string::npos) {
        size_t endPos = jsonContent.find("}", pos);
        if (endPos == std::string::npos)
            break;

        std::string orderStr = jsonContent.substr(pos, endPos - pos + 1);

        Order order;

        size_t startPos = orderStr.find("\"user_id\":");
        size_t endPosUserId = orderStr.find(",", startPos);
        order.user_id =
            orderStr.substr(startPos + 12, endPosUserId - startPos - 12);

        startPos = orderStr.find("\"ordered_at\":");
        size_t endPosOrderedAt = orderStr.find(",", startPos);
        order.ordered_at =
            orderStr.substr(startPos + 15, endPosOrderedAt - startPos - 15);

        startPos = orderStr.find("\"status\":");
        size_t endPosStatus = orderStr.find(",", startPos);
        order.status =
            orderStr.substr(startPos + 11, endPosStatus - startPos - 12);

        startPos = orderStr.find("\"total\":");
        size_t endPosTotal = orderStr.find("}", startPos);
        std::string totalStr =
            orderStr.substr(startPos + 8, endPosTotal - startPos - 8);
        if (!totalStr.empty()) {
            totalStr.erase(
                std::remove_if(totalStr.begin(), totalStr.end(),
                               [](unsigned char x) { return x == '"'; }),
                totalStr.end());
            order.total = std::stod(totalStr);
        } else {
            order.total = 0;
        }

        orders.push_back(order);

        pos = endPos + 1;
    }

    return orders;
}

std::vector<int> findMonths(const std::vector<Order> &orders) {
    std::time_t now = std::time(nullptr);
    std::tm currentTime;
#if defined(_WIN32)
    localtime_s(&currentTime, &now);
#elif defined(__unix__) || defined(__APPLE__)
    localtime_r(&now, &currentTime);
#else
#error "Unknown platform"
#endif

    int lastFullMonth = currentTime.tm_mon;
    int lastFullYear = currentTime.tm_year + 1900;
    if (lastFullMonth == 0) {
        lastFullMonth = 12;
        lastFullYear--;
    }

    int startYear = lastFullYear - 1;
    int endYear = lastFullYear;
    int startMonth = lastFullMonth + 1;
    int endMonth = lastFullMonth;

    if (startMonth > 12) {
        startMonth = 1;
        startYear++;
    }
    if (endMonth == 0) {
        endMonth = 12;
        endYear--;
    }

    std::map<int, double> monthlySpending;

    for (const auto &order : orders) {
        std::istringstream iss(order.ordered_at);
        std::string token;

        std::getline(iss, token, '-');
        int orderYear = std::stoi(token);

        std::getline(iss, token, '-');
        int orderMonth = std::stoi(token);

        if ((orderYear > startYear ||
             (orderYear == startYear && orderMonth >= startMonth)) &&
            (orderYear < endYear ||
             (orderYear == endYear && orderMonth <= endMonth)) &&
            order.status == "COMPLETED") {

            monthlySpending[orderMonth] += order.total;
        }
    }

    int maxValue = 0;
    double maxSpending = 0.0;
    for (const auto &pair : monthlySpending) {
        if (pair.second > maxSpending) {
            maxSpending = pair.second;
            maxValue = pair.first;
        }
    }

    std::vector<int> maxMonths;

    for (const auto &pair : monthlySpending) {
        if (pair.second == maxSpending) {
            maxMonths.push_back(pair.first);
        }
    }

    return maxMonths;
}

void printResult(const std::vector<int> &maxMonths) {
    std::vector<std::string> months = {
        "january", "february", "march",     "april",   "may",      "june",
        "july",    "august",   "september", "october", "november", "december"};

    std::cout << "{\"months\": [";
    for (size_t i = 0; i < maxMonths.size(); ++i) {
        std::cout << "\"" << months[maxMonths[i] - 1]  << "\"";
        if (i != maxMonths.size() - 1) {
            std::cout << ", ";
        }
    }
    std::cout << "]}"; 
}

void createJson(const std::string& outname, const std::vector<int> &maxMonths){
    std::vector<std::string> months = {
        "january", "february", "march",     "april",   "may",      "june",
        "july",    "august",   "september", "october", "november", "december"};


    std::ofstream file(outname);
    if (!file.is_open()) {
        std::cerr << "Failed to open output file." << std::endl;
        return;
    }

    file << "{\"months\": [";
    for (size_t i = 0; i < maxMonths.size(); ++i) {
        file << "\"" << months[maxMonths[i] - 1]  << "\"";
        if (i != maxMonths.size() - 1) {
            file << ", ";
        }
    }
    file << "]}";
}
