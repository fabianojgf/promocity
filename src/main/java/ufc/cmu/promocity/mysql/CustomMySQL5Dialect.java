package ufc.cmu.promocity.mysql;

import java.sql.Types;

import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.BooleanType;
import org.hibernate.type.DoubleType;
import org.hibernate.type.StandardBasicTypes;

public class CustomMySQL5Dialect extends MySQL5Dialect {
	public CustomMySQL5Dialect() {
		super();
		registerColumnType( Types.DOUBLE, "double" );
		registerColumnType( Types.INTEGER, "int" );
		registerColumnType( Types.BOOLEAN, "tinyint(1)" );
		
		registerFunction("distance", new SQLFunctionTemplate(StandardBasicTypes.DOUBLE, "distance(?1,?2,?3,?4,?5)"));
		registerFunction("is_in_radius", new SQLFunctionTemplate(StandardBasicTypes.BOOLEAN, "distance(?1,?2,?3,?4,?5,?6)"));
	}
}
