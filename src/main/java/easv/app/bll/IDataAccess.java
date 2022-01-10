package easv.app.bll;

public interface IDataAccess<DataModel>
{
    DataModel get();
    void submit(DataModel data);
}
