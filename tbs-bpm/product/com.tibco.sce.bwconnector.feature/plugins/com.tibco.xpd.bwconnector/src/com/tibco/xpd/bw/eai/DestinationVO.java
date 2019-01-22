package com.tibco.xpd.bw.eai;

public class DestinationVO {
    private Integer _destination_id = null;
    private Character _protected = null;
    private Integer _provider_id = null;
    private String _name = null;
    private String _target_name = null;
    private Character _target_type = null;
    private String _factory = null;
    
    public DestinationVO(Integer _destination_id, Character _protected, Integer _provider_id, String _name, String _target_name,
            Character _target_type, String _factory)
    {
        this._destination_id = _destination_id;
        this._protected = _protected;
        this._provider_id = _provider_id;
        this._name = _name;
        this._target_name = _target_name;
        this._target_type = _target_type;
        this._factory = _factory;
    }

    public Integer get_destination_id()
    {
        return _destination_id;
    }
    
    public void set_destination_id(Integer _destination_id)
    {
        this._destination_id = _destination_id;
    }
    
    public String get_name()
    {
        return _name;
    }
    
    public void set_name(String _name)
    {
        this._name = _name;
    }
    
    public Character get_protected()
    {
        return _protected;
    }
    
    public void set_protected(Character _protected)
    {
        this._protected = _protected;
    }
    
    public Integer get_provider_id()
    {
        return _provider_id;
    }
    
    public void set_provider_id(Integer _provider_id)
    {
        this._provider_id = _provider_id;
    }
    
    public String get_target_name()
    {
        return _target_name;
    }
    
    public void set_target_name(String _target_name)
    {
        this._target_name = _target_name;
    }
    
    public Character get_target_type()
    {
        return _target_type;
    }
    
    public void set_target_type(Character _target_type)
    {
        this._target_type = _target_type;
    }
    
    public String get_factory()
    {
        return _factory;
    }
    
    public void set_factory(String _factory)
    {
        this._factory = _factory;
    }
}
