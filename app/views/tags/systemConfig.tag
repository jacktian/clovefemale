*{
 *  输出SystemConfiguration的内容
 *    key     (required)   : SystemConfiguration.get(key);
 *    def (opt.)       : SystemConfiguration.get(key, def);
 *
 *    #{systemConfig key:'webtitle' , def:'jingkao.net'/}
}*
%{
    (_arg ) && (_key = _arg);

    if (!_key) {
        throw new play.exceptions.TagInternalException("key attribute cannot be empty for systemConfig tag");
    }
    
	if (!_def) {
		_def = "";
	}
}%${models.SystemConfiguration.get(_key, _def)}