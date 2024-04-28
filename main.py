from src.report import Report

if __name__ == "__main__":
    input_file_name = 'input.json'
    report = Report(input_file_name)
    print(report.find_max_month())
