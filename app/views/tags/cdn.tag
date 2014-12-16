*{
 *  将静态资源地址转换成CDN地址
 *
 *	 <script src="#{cdn /}@{'/public/javascripts/jquery.js'}"></script>
}*
%{
    out.write(utils.CdnUtil.getCdn());
}%