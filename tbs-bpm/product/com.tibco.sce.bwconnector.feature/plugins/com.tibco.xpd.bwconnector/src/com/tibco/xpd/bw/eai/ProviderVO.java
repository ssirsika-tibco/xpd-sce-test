package com.tibco.xpd.bw.eai;

public class ProviderVO {
    private Integer _provider_id = null;
    private Character _protected = null;
    private String _name = null;
    private String _url = null;
    private String _factory = null;
    private String _jar = null;
    private String _jndi_user = null;
    private String _jndi_password = null;
    
    public ProviderVO(Integer _provider_id, Character _protected, String _name, String _url, String _factory, String _jar,
            String _jndi_user, String _jndi_password)
    {
        this._provider_id = _provider_id;
        this._protected = _protected;
        this._name = _name;
        this._url = _url;
        this._factory = _factory;
        this._jar = _jar;
        this._jndi_user = _jndi_user;
        this._jndi_password = _jndi_password;
    }

    public String get_factory()
    {
        return _factory;
    }

    public void set_factory(String _factory)
    {
        this._factory = _factory;
    }

    public String get_jar()
    {
        return _jar;
    }

    public void set_jar(String _jar)
    {
        this._jar = _jar;
    }

    public String get_jndi_password()
    {
        return _jndi_password;
    }

    public void set_jndi_password(String _jndi_password)
    {
        this._jndi_password = _jndi_password;
    }

    public String get_jndi_user()
    {
        return _jndi_user;
    }

    public void set_jndi_user(String _jndi_user)
    {
        this._jndi_user = _jndi_user;
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

    public String get_url()
    {
        return _url;
    }

    public void set_url(String _url)
    {
        this._url = _url;
    }
}
