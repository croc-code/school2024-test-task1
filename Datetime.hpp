#ifndef DATETIME_HPP
#define DATETIME_HPP
#include "MyException.hpp"
struct Datetime{
public:
	unsigned short year;
	unsigned short month;
	unsigned short day;
	unsigned short hour;
	unsigned short minute;
	double second;
	Datetime():
	year(0),
	month(0),
	day(0),
	hour(0),
	minute(0),
	second(0.0)
	{
		
	}
	Datetime(unsigned short _year, unsigned short _month, unsigned short _day, unsigned short _hour, unsigned short _minute, double _second):
	year(_year),
	month(_month),
	day(_day),
	hour(_hour),
	minute(_minute),
	second(_second)
	{
		if(!this->is_valid()){
			throw MyException(-10,"Not valid DateTime");
		}
	}
	bool is_not_null() const{
		return (this->year>0)&&(this->month>0)&&(this->day>0)&&(this->second>=0);
	}
	bool is_valid() const{
		return this->is_not_null() && (this->year<2200)&&(this->year>1950)&&this->is_day_valid()&&(this->hour<24)&&(this->minute<60)&&(this->second<60);
	}
	bool is_day_valid() const{
		if (this->year%4==0 && this->year%100!=0){
			if(this->month==2 && this->day<=29){
				return true;
			}else if(this->month==2){
				return false;
			}
		}else{
			if(this->month==2 && this->day<=28){
				return true;
			}else if(this->month==2){
				return false;
			}
		}
		switch (this->month){
			case 1:
				return this->day<=31;
            	break;
			case 3:
				return this->day<=31;
            	break;
			case 4:
				return this->day<=30;
            	break;
			case 5:
				return this->day<=31;
            	break;
			case 6:
				return this->day<=30;
            	break;
			case 7:
				return this->day<=31;
            	break;
			case 8:
				return this->day<=31;
            	break;
			case 9:
				return this->day<=30;
            	break;
			case 10:
				return this->day<=31;
            	break;
			case 11:
				return this->day<=30;
            	break;
			case 12:
				return this->day<=31;
            	break;
			default:
				return false;
		}
	}
};

#endif