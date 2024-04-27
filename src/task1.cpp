#include "task1.hpp"
#include <iostream>

int main() {
    // Чтение JSON из файла или строки
    std::string inname = "input.json", outname = "output.json";
    std::string jsonContent = getContent(inname);

    // Парсинг JSON
    std::vector<Order> orders = parseJson(jsonContent);

    // Поиск последнего полного месяца
    std::vector<int> maxMonths = findMonths(orders);

    sort(maxMonths.begin(), maxMonths.end());
    // Вывод результата
    printResult(maxMonths);
    createJson(outname, maxMonths);

    return 0;
}
