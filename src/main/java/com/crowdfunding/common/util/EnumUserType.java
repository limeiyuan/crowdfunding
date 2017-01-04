package com.crowdfunding.common.util;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

/**
 * Created by LiMeiyuan on 2016/11/10 15:54.
 * description:
 */
public class EnumUserType implements UserType, ParameterizedType {
    private Method recreateEnumMthd;

    private Method recreateStringMthd;

    private Class enumClass;

    /**
     * This method uses the parameter values passed during enum mapping definition
     * and sets corresponding properties defined
     */
    public void setParameterValues(Properties parameters) {
        if (parameters != null) {
            String enumMthd = parameters.getProperty("recreateEnumMthd");
            String strMthd = parameters.getProperty("recreateStringMthd");
            String className = parameters.getProperty("enumClassName");
            Class<?> returnType;

            try {
                enumClass = Class.forName(className);
                recreateStringMthd = enumClass.getMethod(strMthd);
                returnType = recreateStringMthd.getReturnType();
                recreateEnumMthd = enumClass.getMethod(enumMthd, returnType);
            } catch (ClassNotFoundException | SecurityException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * This method maps the database mapping
     */
    public int[] sqlTypes() {
        return new int[]{Types.CHAR};
    }

    /**
     * This method maps the class for which user type is created
     */
    public Class returnedClass() {
        //return GenderEnum.class;
        return enumClass;
    }


    public boolean equals(Object x, Object y) throws HibernateException {
        return ObjectUtils.nullSafeEquals(x, y);
    }

    /**
     * Fetch the hash code
     */
    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor sessionImplementor, Object o) throws HibernateException, SQLException {
        String value = rs.getString(names[0]);
        Object returnVal = null;

        if (value == null)
            return null;
        else {
            try {
                returnVal = recreateEnumMthd.invoke(enumClass, value);
            } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        //return (GenderEnum)returnVal;
        return returnVal;
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor sessionImplementor) throws HibernateException, SQLException {
        String prepStmtVal;

        if (value == null) {
            st.setObject(index, null);
        } else {
            try {
                prepStmtVal = (String) recreateStringMthd.invoke(value);
                st.setString(index, prepStmtVal);
            } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Deep copy method
     */
    public Object deepCopy(Object value) throws HibernateException {
        if (value == null)
            return null;
        else {
            try {
                return recreateEnumMthd.invoke(value, recreateStringMthd.invoke(value));
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public boolean isMutable() {
        return false;
    }

    public Serializable disassemble(Object value) throws HibernateException {
        Object deepCopy = deepCopy(value);

        if (!(deepCopy instanceof Serializable))
            return (Serializable) deepCopy;

        return null;
    }

    public Object assemble(Serializable cached, Object owner)
            throws HibernateException {
        return deepCopy(cached);
    }

    public Object replace(Object original, Object target, Object owner)
            throws HibernateException {
        return deepCopy(original);
    }
}
