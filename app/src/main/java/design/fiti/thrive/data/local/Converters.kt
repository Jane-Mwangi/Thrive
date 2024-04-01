package design.fiti.thrive.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import design.fiti.thrive.data.remote.dto.ExpenseDto
import design.fiti.thrive.data.remote.util.GsonParser


@ProvidedTypeConverter
class Converters(
    private val jsonParser: GsonParser
) {

    @TypeConverter
    fun fromExpenseIdListJson(json: String): List<ExpenseDto> {
        return jsonParser.fromJson<ArrayList<ExpenseDto>>(
            json, object : TypeToken<ArrayList<ExpenseDto>>() {}.type
        ) ?: emptyList()
    }

    @TypeConverter
    fun toExpenseIdListJson(messages: List<ExpenseDto>): String {
        return jsonParser.toJson(
            messages, object : TypeToken<ArrayList<ExpenseDto>>() {}.type
        ) ?: "[]"
    }

    @TypeConverter
    fun fromList(list: List<Double>): String {
        return list.joinToString(",")
    }

    @TypeConverter
    fun toList(value: String): List<Double> {
        return value.split(",").map { it.toDouble() }
    }

//    @TypeConverter
//    fun fromIncomeIdJson(json: String): IncomeDto? {
//        return jsonParser.fromJson<IncomeDto>(
//            json, object : TypeToken<IncomeDto>() {}.type
//        )
//    }

//    @TypeConverter
//    fun toIncomeIdJson(messages: IncomeDto): String {
//        return jsonParser.toJson(
//            messages, object : TypeToken<IncomeDto>() {}.type
//        ) ?: "[]"
//    }

}