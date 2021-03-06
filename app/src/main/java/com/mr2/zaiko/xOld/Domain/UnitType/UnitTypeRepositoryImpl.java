package com.mr2.zaiko.xOld.Domain.UnitType;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.mr2.zaiko.xOld.Domain.Base.BaseCrudRepository;

import java.util.List;

public class UnitTypeRepositoryImpl extends BaseCrudRepository implements UnitTypeRepository {
    public static final String TAG = UnitTypeRepositoryImpl.class.getSimpleName();

    public UnitTypeRepositoryImpl(Context context) {
        super(context);
    }

    @Override
    public List<Unit> findAllByUnDeleted() {
        Log.d(TAG, "getList");
        Cursor c = adapter.findAllRecordsByNull("m_unit_types", "deleted_at");
        return UnitTypeConverter.convert(c);
    }

    @Override
    public long countByUnDeleted() {
        Log.d(TAG, "countByUnDeleted");
        Cursor cursor = adapter.findAllRecordsByNull("m_unit_type", "deleted_at");
        return cursor.getCount();
    }

    @Override
    public boolean existsByName(String name) {
        Log.d(TAG, "existsByName");
        Cursor c = adapter.findAllRecordExactMatch("m_unit_types", "name", name);
        return c.moveToFirst();
    }

    @Override
    public List<Unit> partialByName(String name) {
        Log.d(TAG, "partialByName");
        Cursor c = adapter.findAllRecordPartialMatch("m_unit_types", "name", name);
        return UnitTypeConverter.convert(c);
    }

    @Override
    public Unit findOne(Integer _id) {
        Log.d(TAG, "findOne");
        Cursor c = adapter.findOneRecordById("m_unit_types", _id);
        List<Unit> list = UnitTypeConverter.convert(c);
        if (null == list){
            return null;
        }
        return list.get(0);
    }

    @Override
    public boolean exists(Integer _id) {
        Log.d(TAG, "existsData");
        Cursor c = adapter.findOneRecordById("m_unit_types", _id);
        return c.moveToFirst();
    }

    @Override
    public List<Unit> findAll() {
        Log.d(TAG, "findAll");
        Cursor c = adapter.getAllRecords("m_unit_types");
        return UnitTypeConverter.convert(c);
    }

    @Override
    public long count() {
        Log.d(TAG, "count");
        Cursor c = adapter.getAllRecords("m_unit_types");
        return c.getCount();
    }

    @Override
    public Unit save(Unit entity) {
        Log.d(TAG, "save");
        Log.d(TAG, "gat entity, " + entity.toString());
        ContentValues values = UnitTypeConverter.convert(entity);
        adapter.beginTransaction();
        int result = (int) adapter.insertRecords("m_unit_types", values);
        Log.d(TAG, "result:" + result);
        if(result != -1){
            Log.d(TAG, "collect save DB.");
            adapter.commit();
            return findOne(result);
        }
        Log.d(TAG, "miss save DB.");
        adapter.rollBack();
        throw new IllegalStateException("登録に失敗しました。Unit{" + entity.toString() + "}");
    }

    @Override
    public void delete(Unit entity) {
        Log.d(TAG, "delete");
        adapter.updateRecordsByCurrentTimestamp("m_unit_types", "deleted_at", entity.get_id().value());
    }
}
