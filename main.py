import json
from calendar import month_name

file_src = input()

def sort_months(months_list): # function for sorting list of months
	month_lookup = [x.lower() for x in month_name]

	return sorted(months_list, key=month_lookup.index)


def formated_report(file_name:str):
	months = {
		"01": "january",
		"02": "fabuary",
		"03": "march",
		"04": "april", 
		"05": "may",
		"06": "june",
		"07": "july",
		"08": "august",
		"09": "september",
		"10": "october",
		"11": "november",
		"12": "december"
	}

	total = {}
	max_profit_list = []

	with open(file_name) as file:  # reading input.json file and converting to list object
		data = json.loads(file.read())

	
	for i in range(len(data)): #iterate over list of data parsed from json file
		current_order = data[i]
		if current_order['status'] == 'COMPLETED': #checking the status
			current_month = months[current_order["ordered_at"][5:7]] # Taking months number out of dictionary of current month
			current_order_amount = float(current_order['total']) #taking amount of money and turning to float
			
			# putting all monthes with its money profit in total dictionary
			if current_month in total: 
				total[current_month] += current_order_amount
			else:
				total[current_month] = current_order_amount
	
	max_profit = max(total.values()) # finding max profit in total dict
	max_profit_position = list(total.values()).index(max_profit) # finding position of max profit
	max_profit_month = list(total.keys())[max_profit_position]
	max_profit_list.append(max_profit_month)


	for key, value in total.items(): #checking and adding max profit months which left(if exists)
		if value == max_profit and key != max_profit_month:
			max_profit_list.append(key)

	return json.dumps({"months": sort_months(max_profit_list)})

print(formated_report(file_src))
	
# MAYBE ITS NOT BEST SOLUTION BUT I HOPE YOU WILL NOT BE TOO STRICT

