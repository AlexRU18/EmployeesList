package ru.alexsuvorov.employee_service.db;

public interface onDbListeners {

    void onOperationSuccess(String tableName, int operation, Object obj);

    void onOperationFailed(String tableName, int operation);
}