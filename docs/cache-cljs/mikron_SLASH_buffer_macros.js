goog.provide("mikron.buffer_macros");
/**
 * Executes `body` and updates the position `pos` with the delta `delta`.
 */
(function (){
mikron.buffer_macros.with_delta = (function mikron$buffer_macros$with_delta(_AMPERSAND_form,_AMPERSAND_env,pos,delta,body){
var value = cljs.core.with_meta.call(null,cljs.core.gensym.call(null,"value"),new cljs.core.PersistentArrayMap(null, 5, [new cljs.core.Keyword(null,"file","file",(-1269645878)),mikron.buffer_macros,new cljs.core.Keyword(null,"line","line",(212345235)),(10),new cljs.core.Keyword(null,"column","column",(2078222095)),(31),new cljs.core.Keyword(null,"end-line","end-line",(1837326455)),(10),new cljs.core.Keyword(null,"end-column","end-column",(1425389514)),(36)], null));
return cljs.core.sequence.call(null,cljs.core.concat.call(null,cljs.core._conj.call(null,cljs.core.List.EMPTY,new cljs.core.Symbol("cljs.core","let","cljs.core/let",(-308701135),null)),(function (){var x__25689__auto__ = cljs.core.vec.call(null,cljs.core.sequence.call(null,cljs.core.concat.call(null,(function (){var x__25689__auto__ = value;
return cljs.core._conj.call(null,cljs.core.List.EMPTY,x__25689__auto__);
})(),(function (){var x__25689__auto__ = body;
return cljs.core._conj.call(null,cljs.core.List.EMPTY,x__25689__auto__);
})())));
return cljs.core._conj.call(null,cljs.core.List.EMPTY,x__25689__auto__);
})(),(function (){var x__25689__auto__ = cljs.core.sequence.call(null,cljs.core.concat.call(null,cljs.core._conj.call(null,cljs.core.List.EMPTY,new cljs.core.Symbol(null,"set!","set!",(250714521),null)),(function (){var x__25689__auto__ = pos;
return cljs.core._conj.call(null,cljs.core.List.EMPTY,x__25689__auto__);
})(),(function (){var x__25689__auto__ = cljs.core.sequence.call(null,cljs.core.concat.call(null,cljs.core._conj.call(null,cljs.core.List.EMPTY,new cljs.core.Symbol("cljs.core","unchecked-add","cljs.core/unchecked-add",(1865931960),null)),(function (){var x__25689__auto__ = pos;
return cljs.core._conj.call(null,cljs.core.List.EMPTY,x__25689__auto__);
})(),(function (){var x__25689__auto__ = delta;
return cljs.core._conj.call(null,cljs.core.List.EMPTY,x__25689__auto__);
})()));
return cljs.core._conj.call(null,cljs.core.List.EMPTY,x__25689__auto__);
})()));
return cljs.core._conj.call(null,cljs.core.List.EMPTY,x__25689__auto__);
})(),(function (){var x__25689__auto__ = value;
return cljs.core._conj.call(null,cljs.core.List.EMPTY,x__25689__auto__);
})()));
}); return (
new cljs.core.Var(function(){return mikron.buffer_macros.with_delta;},new cljs.core.Symbol("mikron.buffer-macros","with-delta","mikron.buffer-macros/with-delta",(1443792221),null),cljs.core.PersistentHashMap.fromArrays([new cljs.core.Keyword(null,"ns","ns",(441598760)),new cljs.core.Keyword(null,"name","name",(1843675177)),new cljs.core.Keyword(null,"file","file",(-1269645878)),new cljs.core.Keyword(null,"end-column","end-column",(1425389514)),new cljs.core.Keyword(null,"column","column",(2078222095)),new cljs.core.Keyword(null,"line","line",(212345235)),new cljs.core.Keyword(null,"macro","macro",(-867863404)),new cljs.core.Keyword(null,"end-line","end-line",(1837326455)),new cljs.core.Keyword(null,"arglists","arglists",(1661989754)),new cljs.core.Keyword(null,"doc","doc",(1913296891)),new cljs.core.Keyword(null,"test","test",(577538877))],[new cljs.core.Symbol(null,"mikron.buffer-macros","mikron.buffer-macros",(-514939989),null),new cljs.core.Symbol(null,"with-delta","with-delta",(-1102625563),null),"mikron/buffer_macros.cljc",(21),(1),(7),true,(7),cljs.core.list(new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Symbol(null,"pos","pos",(775924307),null),new cljs.core.Symbol(null,"delta","delta",(1749471484),null),new cljs.core.Symbol(null,"body","body",(-408674142),null)], null)),"Executes `body` and updates the position `pos` with the delta `delta`.",(cljs.core.truth_(mikron.buffer_macros.with_delta)?mikron.buffer_macros.with_delta.cljs$lang$test:null)])));})()
;

mikron.buffer_macros.with_delta.cljs$lang$macro = true;
/**
 * Executes the expressions with the endianness automatically set to `le`.
 */
(function (){
mikron.buffer_macros.with_le = (function mikron$buffer_macros$with_le(_AMPERSAND_form,_AMPERSAND_env,le,p__215){
var vec__219 = p__215;
var seq__220 = cljs.core.seq.call(null,vec__219);
var first__221 = cljs.core.first.call(null,seq__220);
var seq__220__$1 = cljs.core.next.call(null,seq__220);
var expr = first__221;
var exprs = seq__220__$1;
return cljs.core.sequence.call(null,cljs.core.concat.call(null,cljs.core._conj.call(null,cljs.core.List.EMPTY,new cljs.core.Symbol(null,"if","if",(1181717262),null)),(function (){var x__25689__auto__ = le;
return cljs.core._conj.call(null,cljs.core.List.EMPTY,x__25689__auto__);
})(),(function (){var x__25689__auto__ = cljs.core.sequence.call(null,cljs.core.concat.call(null,(function (){var x__25689__auto__ = cljs.core.symbol.call(null,[cljs.core.str.cljs$core$IFn$_invoke$arity$1(expr),cljs.core.str.cljs$core$IFn$_invoke$arity$1("LE")].join(''));
return cljs.core._conj.call(null,cljs.core.List.EMPTY,x__25689__auto__);
})(),exprs));
return cljs.core._conj.call(null,cljs.core.List.EMPTY,x__25689__auto__);
})(),(function (){var x__25689__auto__ = cljs.core.sequence.call(null,cljs.core.concat.call(null,(function (){var x__25689__auto__ = cljs.core.symbol.call(null,[cljs.core.str.cljs$core$IFn$_invoke$arity$1(expr),cljs.core.str.cljs$core$IFn$_invoke$arity$1("BE")].join(''));
return cljs.core._conj.call(null,cljs.core.List.EMPTY,x__25689__auto__);
})(),exprs));
return cljs.core._conj.call(null,cljs.core.List.EMPTY,x__25689__auto__);
})()));
}); return (
new cljs.core.Var(function(){return mikron.buffer_macros.with_le;},new cljs.core.Symbol("mikron.buffer-macros","with-le","mikron.buffer-macros/with-le",(1764565824),null),cljs.core.PersistentHashMap.fromArrays([new cljs.core.Keyword(null,"ns","ns",(441598760)),new cljs.core.Keyword(null,"name","name",(1843675177)),new cljs.core.Keyword(null,"file","file",(-1269645878)),new cljs.core.Keyword(null,"end-column","end-column",(1425389514)),new cljs.core.Keyword(null,"column","column",(2078222095)),new cljs.core.Keyword(null,"line","line",(212345235)),new cljs.core.Keyword(null,"macro","macro",(-867863404)),new cljs.core.Keyword(null,"end-line","end-line",(1837326455)),new cljs.core.Keyword(null,"arglists","arglists",(1661989754)),new cljs.core.Keyword(null,"doc","doc",(1913296891)),new cljs.core.Keyword(null,"test","test",(577538877))],[new cljs.core.Symbol(null,"mikron.buffer-macros","mikron.buffer-macros",(-514939989),null),new cljs.core.Symbol(null,"with-le","with-le",(15197368),null),"mikron/buffer_macros.cljc",(18),(1),(15),true,(15),cljs.core.list(new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Symbol(null,"le","le",(1421379234),null),new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Symbol(null,"expr","expr",(-1908713478),null),new cljs.core.Symbol(null,"&","&",(-2144855648),null),new cljs.core.Symbol(null,"exprs","exprs",(-858606675),null)], null)], null)),"Executes the expressions with the endianness automatically set to `le`.",(cljs.core.truth_(mikron.buffer_macros.with_le)?mikron.buffer_macros.with_le.cljs$lang$test:null)])));})()
;

mikron.buffer_macros.with_le.cljs$lang$macro = true;
/**
 * Expands to a `definterface` call in clj, `defprotocol` call in cljs.
 */
(function (){
mikron.buffer_macros.definterface_PLUS_ = (function mikron$buffer_macros$definterface_PLUS_(var_args){
var args__25948__auto__ = [];
var len__25946__auto___238 = arguments.length;
var i__25947__auto___239 = (0);
while(true){
if((i__25947__auto___239 < len__25946__auto___238)){
args__25948__auto__.push((arguments[i__25947__auto___239]));

var G__240 = (i__25947__auto___239 + (1));
i__25947__auto___239 = G__240;
continue;
} else {
}
break;
}

var argseq__25949__auto__ = ((((3) < args__25948__auto__.length))?(new cljs.core.IndexedSeq(args__25948__auto__.slice((3)),(0),null)):null);
return mikron.buffer_macros.definterface_PLUS_.cljs$core$IFn$_invoke$arity$variadic((arguments[(0)]),(arguments[(1)]),(arguments[(2)]),argseq__25949__auto__);
}); return (
new cljs.core.Var(function(){return mikron.buffer_macros.definterface_PLUS_;},new cljs.core.Symbol("mikron.buffer-macros","definterface+","mikron.buffer-macros/definterface+",(133272957),null),cljs.core.PersistentHashMap.fromArrays([new cljs.core.Keyword(null,"ns","ns",(441598760)),new cljs.core.Keyword(null,"name","name",(1843675177)),new cljs.core.Keyword(null,"file","file",(-1269645878)),new cljs.core.Keyword(null,"end-column","end-column",(1425389514)),new cljs.core.Keyword(null,"top-fn","top-fn",(-2056129173)),new cljs.core.Keyword(null,"column","column",(2078222095)),new cljs.core.Keyword(null,"line","line",(212345235)),new cljs.core.Keyword(null,"macro","macro",(-867863404)),new cljs.core.Keyword(null,"end-line","end-line",(1837326455)),new cljs.core.Keyword(null,"arglists","arglists",(1661989754)),new cljs.core.Keyword(null,"doc","doc",(1913296891)),new cljs.core.Keyword(null,"test","test",(577538877))],[new cljs.core.Symbol(null,"mikron.buffer-macros","mikron.buffer-macros",(-514939989),null),new cljs.core.Symbol(null,"definterface+","definterface+",(1596413445),null),"mikron/buffer_macros.cljc",(24),new cljs.core.PersistentArrayMap(null, 5, [new cljs.core.Keyword(null,"variadic","variadic",(882626057)),true,new cljs.core.Keyword(null,"max-fixed-arity","max-fixed-arity",(-690205543)),(3),new cljs.core.Keyword(null,"method-params","method-params",(-980792179)),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [cljs.core.list(new cljs.core.Symbol(null,"&form","&form",(1482799337),null),new cljs.core.Symbol(null,"&env","&env",(-919163083),null),new cljs.core.Symbol(null,"name","name",(-810760592),null),new cljs.core.Symbol(null,"ops","ops",(-1417105706),null))], null),new cljs.core.Keyword(null,"arglists","arglists",(1661989754)),cljs.core.list(new cljs.core.PersistentVector(null, 5, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Symbol(null,"&form","&form",(1482799337),null),new cljs.core.Symbol(null,"&env","&env",(-919163083),null),new cljs.core.Symbol(null,"name","name",(-810760592),null),new cljs.core.Symbol(null,"&","&",(-2144855648),null),new cljs.core.Symbol(null,"ops","ops",(-1417105706),null)], null)),new cljs.core.Keyword(null,"arglists-meta","arglists-meta",(1944829838)),cljs.core.list(null)], null),(1),(22),true,(22),cljs.core.list(new cljs.core.PersistentVector(null, 5, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Symbol(null,"&form","&form",(1482799337),null),new cljs.core.Symbol(null,"&env","&env",(-919163083),null),new cljs.core.Symbol(null,"name","name",(-810760592),null),new cljs.core.Symbol(null,"&","&",(-2144855648),null),new cljs.core.Symbol(null,"ops","ops",(-1417105706),null)], null)),"Expands to a `definterface` call in clj, `defprotocol` call in cljs.",(cljs.core.truth_(mikron.buffer_macros.definterface_PLUS_)?mikron.buffer_macros.definterface_PLUS_.cljs$lang$test:null)])));})()
;

mikron.buffer_macros.definterface_PLUS_.cljs$core$IFn$_invoke$arity$variadic = (function (_AMPERSAND_form,_AMPERSAND_env,name,ops){
var no_meta = (function (p1__7_SHARP_){
return cljs.core.with_meta.call(null,p1__7_SHARP_,null);
});
var cljs_QMARK_ = cljs.core.boolean$.call(null,new cljs.core.Keyword(null,"ns","ns",(441598760)).cljs$core$IFn$_invoke$arity$1(_AMPERSAND_env));
var ops__$1 = cljs.core.map.call(null,((function (no_meta,cljs_QMARK_){
return (function (p__226){
var vec__227 = p__226;
var op_name = cljs.core.nth.call(null,vec__227,(0),null);
var args = cljs.core.nth.call(null,vec__227,(1),null);
var doc_string = cljs.core.nth.call(null,vec__227,(2),null);
return new cljs.core.PersistentVector(null, 4, 5, cljs.core.PersistentVector.EMPTY_NODE, [op_name,args,cljs.core.vec.call(null,cljs.core.cons.call(null,new cljs.core.Symbol(null,"this","this",(1028897902),null),cljs.core.map.call(null,no_meta,args))),(cljs.core.truth_(doc_string)?new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [doc_string], null):null)], null);
});})(no_meta,cljs_QMARK_))
,ops);
var inner_form = cljs.core.sequence.call(null,cljs.core.concat.call(null,(function (){var x__25689__auto__ = ((cljs_QMARK_)?new cljs.core.Symbol("cljs.core","defprotocol","cljs.core/defprotocol",(1787210635),null):new cljs.core.Symbol("mikron.buffer-macros","definterface","mikron.buffer-macros/definterface",(-1437711677),null));
return cljs.core._conj.call(null,cljs.core.List.EMPTY,x__25689__auto__);
})(),(function (){var x__25689__auto__ = name;
return cljs.core._conj.call(null,cljs.core.List.EMPTY,x__25689__auto__);
})(),cljs.core.map.call(null,((function (no_meta,cljs_QMARK_,ops__$1){
return (function (p__230){
var vec__231 = p__230;
var op_name = cljs.core.nth.call(null,vec__231,(0),null);
var args = cljs.core.nth.call(null,vec__231,(1),null);
var args_SINGLEQUOTE_ = cljs.core.nth.call(null,vec__231,(2),null);
var doc_string = cljs.core.nth.call(null,vec__231,(3),null);
if(cljs_QMARK_){
return cljs.core.sequence.call(null,cljs.core.concat.call(null,(function (){var x__25689__auto__ = no_meta.call(null,op_name);
return cljs.core._conj.call(null,cljs.core.List.EMPTY,x__25689__auto__);
})(),(function (){var x__25689__auto__ = args_SINGLEQUOTE_;
return cljs.core._conj.call(null,cljs.core.List.EMPTY,x__25689__auto__);
})(),doc_string));
} else {
return cljs.core.sequence.call(null,cljs.core.concat.call(null,(function (){var x__25689__auto__ = cljs.core.with_meta.call(null,cljs.core.munge.call(null,op_name),cljs.core.meta.call(null,op_name));
return cljs.core._conj.call(null,cljs.core.List.EMPTY,x__25689__auto__);
})(),(function (){var x__25689__auto__ = args;
return cljs.core._conj.call(null,cljs.core.List.EMPTY,x__25689__auto__);
})(),doc_string));
}
});})(no_meta,cljs_QMARK_,ops__$1))
,ops__$1)));
if(cljs_QMARK_){
return inner_form;
} else {
return cljs.core.sequence.call(null,cljs.core.concat.call(null,cljs.core._conj.call(null,cljs.core.List.EMPTY,new cljs.core.Symbol(null,"do","do",(1686842252),null)),(function (){var x__25689__auto__ = inner_form;
return cljs.core._conj.call(null,cljs.core.List.EMPTY,x__25689__auto__);
})(),cljs.core.map.call(null,((function (no_meta,cljs_QMARK_,ops__$1,inner_form){
return (function (p__234){
var vec__235 = p__234;
var op_name = cljs.core.nth.call(null,vec__235,(0),null);
var args = cljs.core.nth.call(null,vec__235,(1),null);
var args_SINGLEQUOTE_ = cljs.core.nth.call(null,vec__235,(2),null);
var doc_string = cljs.core.nth.call(null,vec__235,(3),null);
return cljs.core.sequence.call(null,cljs.core.concat.call(null,cljs.core._conj.call(null,cljs.core.List.EMPTY,new cljs.core.Symbol("cljs.core","defn","cljs.core/defn",(-1606493717),null)),(function (){var x__25689__auto__ = no_meta.call(null,op_name);
return cljs.core._conj.call(null,cljs.core.List.EMPTY,x__25689__auto__);
})(),(function (){var x__25689__auto__ = cljs.core.apply.call(null,cljs.core.array_map,cljs.core.sequence.call(null,cljs.core.concat.call(null,cljs.core._conj.call(null,cljs.core.List.EMPTY,new cljs.core.Keyword(null,"inline","inline",(1399884222))),(function (){var x__25689__auto__ = cljs.core.sequence.call(null,cljs.core.concat.call(null,cljs.core._conj.call(null,cljs.core.List.EMPTY,new cljs.core.Symbol("cljs.core","fn","cljs.core/fn",(-1065745098),null)),(function (){var x__25689__auto__ = args_SINGLEQUOTE_;
return cljs.core._conj.call(null,cljs.core.List.EMPTY,x__25689__auto__);
})(),(function (){var x__25689__auto__ = cljs.core.sequence.call(null,cljs.core.concat.call(null,cljs.core._conj.call(null,cljs.core.List.EMPTY,new cljs.core.Symbol("cljs.core","sequence","cljs.core/sequence",(1908459032),null)),(function (){var x__25689__auto__ = cljs.core.sequence.call(null,cljs.core.concat.call(null,cljs.core._conj.call(null,cljs.core.List.EMPTY,new cljs.core.Symbol("cljs.core","concat","cljs.core/concat",(-1133584918),null)),(function (){var x__25689__auto__ = cljs.core.sequence.call(null,cljs.core.concat.call(null,cljs.core._conj.call(null,cljs.core.List.EMPTY,new cljs.core.Symbol("cljs.core","list","cljs.core/list",(-1331406371),null)),(function (){var x__25689__auto__ = cljs.core.sequence.call(null,cljs.core.concat.call(null,cljs.core._conj.call(null,cljs.core.List.EMPTY,new cljs.core.Symbol(null,"quote","quote",(1377916282),null)),(function (){var x__25689__auto__ = cljs.core.symbol.call(null,[cljs.core.str.cljs$core$IFn$_invoke$arity$1("."),cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljs.core.munge.call(null,op_name))].join(''));
return cljs.core._conj.call(null,cljs.core.List.EMPTY,x__25689__auto__);
})()));
return cljs.core._conj.call(null,cljs.core.List.EMPTY,x__25689__auto__);
})()));
return cljs.core._conj.call(null,cljs.core.List.EMPTY,x__25689__auto__);
})(),(function (){var x__25689__auto__ = cljs.core.sequence.call(null,cljs.core.concat.call(null,cljs.core._conj.call(null,cljs.core.List.EMPTY,new cljs.core.Symbol("cljs.core","list","cljs.core/list",(-1331406371),null)),args_SINGLEQUOTE_));
return cljs.core._conj.call(null,cljs.core.List.EMPTY,x__25689__auto__);
})()));
return cljs.core._conj.call(null,cljs.core.List.EMPTY,x__25689__auto__);
})()));
return cljs.core._conj.call(null,cljs.core.List.EMPTY,x__25689__auto__);
})()));
return cljs.core._conj.call(null,cljs.core.List.EMPTY,x__25689__auto__);
})())));
return cljs.core._conj.call(null,cljs.core.List.EMPTY,x__25689__auto__);
})(),(function (){var x__25689__auto__ = cljs.core.with_meta.call(null,cljs.core.vec.call(null,cljs.core.cons.call(null,cljs.core.with_meta.call(null,new cljs.core.Symbol(null,"this","this",(1028897902),null),new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"tag","tag",(-1290361223)),name], null)),args)),cljs.core.meta.call(null,op_name));
return cljs.core._conj.call(null,cljs.core.List.EMPTY,x__25689__auto__);
})(),(function (){var x__25689__auto__ = cljs.core.sequence.call(null,cljs.core.concat.call(null,(function (){var x__25689__auto__ = cljs.core.symbol.call(null,[cljs.core.str.cljs$core$IFn$_invoke$arity$1("."),cljs.core.str.cljs$core$IFn$_invoke$arity$1(cljs.core.munge.call(null,op_name))].join(''));
return cljs.core._conj.call(null,cljs.core.List.EMPTY,x__25689__auto__);
})(),args_SINGLEQUOTE_));
return cljs.core._conj.call(null,cljs.core.List.EMPTY,x__25689__auto__);
})()));
});})(no_meta,cljs_QMARK_,ops__$1,inner_form))
,ops__$1)));
}
});

mikron.buffer_macros.definterface_PLUS_.cljs$lang$maxFixedArity = (3);

mikron.buffer_macros.definterface_PLUS_.cljs$lang$applyTo = (function (seq222){
var G__223 = cljs.core.first.call(null,seq222);
var seq222__$1 = cljs.core.next.call(null,seq222);
var G__224 = cljs.core.first.call(null,seq222__$1);
var seq222__$2 = cljs.core.next.call(null,seq222__$1);
var G__225 = cljs.core.first.call(null,seq222__$2);
var seq222__$3 = cljs.core.next.call(null,seq222__$2);
return mikron.buffer_macros.definterface_PLUS_.cljs$core$IFn$_invoke$arity$variadic(G__223,G__224,G__225,seq222__$3);
});

new cljs.core.Var(function(){return mikron.buffer_macros.definterface_PLUS_;},new cljs.core.Symbol("mikron.buffer-macros","definterface+","mikron.buffer-macros/definterface+",(133272957),null),cljs.core.PersistentHashMap.fromArrays([new cljs.core.Keyword(null,"ns","ns",(441598760)),new cljs.core.Keyword(null,"name","name",(1843675177)),new cljs.core.Keyword(null,"file","file",(-1269645878)),new cljs.core.Keyword(null,"end-column","end-column",(1425389514)),new cljs.core.Keyword(null,"top-fn","top-fn",(-2056129173)),new cljs.core.Keyword(null,"column","column",(2078222095)),new cljs.core.Keyword(null,"line","line",(212345235)),new cljs.core.Keyword(null,"macro","macro",(-867863404)),new cljs.core.Keyword(null,"end-line","end-line",(1837326455)),new cljs.core.Keyword(null,"arglists","arglists",(1661989754)),new cljs.core.Keyword(null,"doc","doc",(1913296891)),new cljs.core.Keyword(null,"test","test",(577538877))],[new cljs.core.Symbol(null,"mikron.buffer-macros","mikron.buffer-macros",(-514939989),null),new cljs.core.Symbol(null,"definterface+","definterface+",(1596413445),null),"mikron/buffer_macros.cljc",(24),new cljs.core.PersistentArrayMap(null, 5, [new cljs.core.Keyword(null,"variadic","variadic",(882626057)),true,new cljs.core.Keyword(null,"max-fixed-arity","max-fixed-arity",(-690205543)),(3),new cljs.core.Keyword(null,"method-params","method-params",(-980792179)),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [cljs.core.list(new cljs.core.Symbol(null,"&form","&form",(1482799337),null),new cljs.core.Symbol(null,"&env","&env",(-919163083),null),new cljs.core.Symbol(null,"name","name",(-810760592),null),new cljs.core.Symbol(null,"ops","ops",(-1417105706),null))], null),new cljs.core.Keyword(null,"arglists","arglists",(1661989754)),cljs.core.list(new cljs.core.PersistentVector(null, 5, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Symbol(null,"&form","&form",(1482799337),null),new cljs.core.Symbol(null,"&env","&env",(-919163083),null),new cljs.core.Symbol(null,"name","name",(-810760592),null),new cljs.core.Symbol(null,"&","&",(-2144855648),null),new cljs.core.Symbol(null,"ops","ops",(-1417105706),null)], null)),new cljs.core.Keyword(null,"arglists-meta","arglists-meta",(1944829838)),cljs.core.list(null)], null),(1),(22),true,(22),cljs.core.list(new cljs.core.PersistentVector(null, 5, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Symbol(null,"&form","&form",(1482799337),null),new cljs.core.Symbol(null,"&env","&env",(-919163083),null),new cljs.core.Symbol(null,"name","name",(-810760592),null),new cljs.core.Symbol(null,"&","&",(-2144855648),null),new cljs.core.Symbol(null,"ops","ops",(-1417105706),null)], null)),"Expands to a `definterface` call in clj, `defprotocol` call in cljs.",(cljs.core.truth_(mikron.buffer_macros.definterface_PLUS_)?mikron.buffer_macros.definterface_PLUS_.cljs$lang$test:null)]));

mikron.buffer_macros.definterface_PLUS_.cljs$lang$macro = true;
cljs.spec.def_impl.call(null,new cljs.core.Symbol("mikron.buffer-macros","definterface+","mikron.buffer-macros/definterface+",(133272957),null),cljs.core.list(new cljs.core.Symbol("cljs.spec","fspec","cljs.spec/fspec",(982220571),null),new cljs.core.Keyword(null,"args","args",(1315556576)),new cljs.core.Keyword("mikron.spec","definterface+-args","mikron.spec/definterface+-args",(540411213))),cljs.spec.fspec_impl.call(null,cljs.spec.spec_impl.call(null,new cljs.core.Keyword("mikron.spec","definterface+-args","mikron.spec/definterface+-args",(540411213)),new cljs.core.Keyword("mikron.spec","definterface+-args","mikron.spec/definterface+-args",(540411213)),null,null),new cljs.core.Keyword("mikron.spec","definterface+-args","mikron.spec/definterface+-args",(540411213)),null,null,null,null,null));
