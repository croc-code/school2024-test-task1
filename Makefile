task1: main_task1.o analize_task1.o deserialize_transaction.o input_file.o output_file.o serialize_months.o
	g++ main_task1.o input_file.o deserialize_transaction.o analize_task1.o serialize_months.o output_file.o -o task1
	
libra: task1.hpp MyException.hpp Datetime.hpp Transaction.hpp

main_task1.o: libra main_task1.cpp
	g++ -c main_task1.cpp

input_file.o: libra input_file.cpp
	g++ -c input_file.cpp

deserialize_transaction.o: libra deserialize_transaction.cpp
	g++ -c deserialize_transaction.cpp

analize_task1.o: libra analize_task1.cpp
	g++ -c analize_task1.cpp

serialize_months.o: libra serialize_months.cpp
	g++ -c serialize_months.cpp

output_file.o: libra output_file.cpp
	g++ -c output_file.cpp
