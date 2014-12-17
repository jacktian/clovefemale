*{
 *  输出Play.configuration的内容
 *    key     (required)   : Play.configuration.get(key);
 *    def (opt.)       : Play.configuration.getProperty(key, def);
 *
 *    #{conf key:'http.port' , def:'9000'/}
 *	  #{conf 'http.port'/}
}*
%{
    (_arg ) && (_key = _arg);

    if (!_key) {
        throw new play.exceptions.TagInternalException("key attribute cannot be empty for conf tag");
    }
    
	if (!_def) {
		_def = "";
	}
}%${play.Play.configuration.getProperty(_key, _def)}