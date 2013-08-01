package jp.co.dk.browzer.http.header;

/**
 * ContentsTypeは、HTTP通信にて使用されるHTTPヘッダの"Contents-Type"に含まれるファイル種別を定義する定数クラス
 * 
 * @version 1.0
 * @author D.Kanno
 */
public enum ContentsType {
	TEXT_HTML("text/html",new String[]{"html","htm"}),
	TEXT_XML("text/xml",null),
	TEXT_CSS("text/css",new String[]{"css"}),
	IMAGE_JPEG("image/jpeg",new String[]{"jpeg","jpg","jpe"}),
	IMAGE_GIF("image/gif",new String[]{"gif"}),
	IMAGE_TIFF("image/tiff",new String[]{"tiff","tif"}),
	IMAGE_PNG("image/png",new String[]{"png"}),
	IMAGE_IEF("image/ief",new String[]{"ief"}),
	IMAGE_BMP("image/bmp",new String[]{"bmp"}),
	IMAGE_CGM("image/cgm",new String[]{"cgm"}),
	IMAGE_G3FAX("image/g3fax",null),
	IMAGE_NAPLPS("image/naplps",null),
	IMAGE_PRS_BTIF("image/prs.btif",null),
	IMAGE_PRS_PTI("image/prs.pti",null),
	IMAGE_SVG_XML("image/svg+xml",new String[]{"svg"}),
	IMAGE_T38("image/t38",null),
	TEXT_CALENDAR("text/calendar",new String[]{"ics","ifb"}),
	TEXT_DIRECTORY("text/directory",null),
	TEXT_ENRICHED("text/enriched",null),
	TEXT_PARITYFEC("text/parityfec",null),
	TEXT_PLAIN("text/plain",new String[]{"asc","txt"}),
	TEXT_PRS_LINES_TAG("text/prs.lines.tag",null),
	TEXT_RFC822_HEADERS("text/rfc822-headers",null),
	TEXT_RICHTEXT("text/richtext",new String[]{"rtx"}),
	TEXT_RTF("text/rtf",new String[]{"rtf"}),
	TEXT_SGML("text/sgml",new String[]{"sgml","sgm"}),
	TEXT_T140("text/t140",null),
	TEXT_TAB_SEPARATED_VALUES("text/tab-separated-values",new String[]{"tsv"}),
	TEXT_URI_LIST("text/uri-list",null),
	TEXT_VND_ABC("text/vnd.abc",null),
	TEXT_VND_CURL("text/vnd.curl",null),
	TEXT_VND_DMCLIENTSCRIPT("text/vnd.dmclientscript",null),
	TEXT_VND_FLY("text/vnd.fly",null),
	TEXT_VND_FMI_FLEXSTOR("text/vnd.fmi.flexstor",null),
	TEXT_VND_IN3D_3DML("text/vnd.in3d.3dml",null),
	TEXT_VND_IN3D_SPOT("text/vnd.in3d.spot",null),
	TEXT_VND_IPTC_NITF("text/vnd.iptc.nitf",null),
	TEXT_VND_IPTC_NEWSML("text/vnd.iptc.newsml",null),
	TEXT_VND_LATEX_Z("text/vnd.latex-z",null),
	TEXT_VND_MOTOROLA_REFLEX("text/vnd.motorola.reflex",null),
	TEXT_VND_MS_MEDIAPACKAGE("text/vnd.ms-mediapackage",null),
	TEXT_VND_NET2PHONE_COMMCENTER_COMMAND("text/vnd.net2phone.commcenter.command",null),
	TEXT_VND_SUN_J2ME_APP_DESCRIPTOR("text/vnd.sun.j2me.app-descriptor",null),
	TEXT_VND_WAP_SI("text/vnd.wap.si",null),
	TEXT_VND_WAP_SL("text/vnd.wap.sl",null),
	TEXT_VND_WAP_WML("text/vnd.wap.wml",new String[]{"wml"}),
	TEXT_VND_WAP_WMLSCRIPT("text/vnd.wap.wmlscript",new String[]{"wmls"}),
	TEXT_X_SETEXT("text/x-setext",new String[]{"etx"}),
	TEXT_XML_EXTERNAL_PARSED_ENTITY("text/xml-external-parsed-entity",null),
	TEXT_X_SERVER_PARSED_HTML("text/x-server-parsed-html",new String[]{"shtml"}),
	APPLICATION_ACTIVEMESSAGE("application/activemessage",null),
	APPLICATION_ANDREW_INSET("application/andrew-inset",new String[]{"ez"}),
	APPLICATION_APPLEFILE("application/applefile",null),
	APPLICATION_ATOM_XML("application/atom+xml",new String[]{"atom"}),
	APPLICATION_ATOMICMAIL("application/atomicmail",null),
	APPLICATION_BATCH_SMTP("application/batch-smtp",null),
	APPLICATION_BEEP_XML("application/beep+xml",null),
	APPLICATION_CALS_1840("application/cals-1840",null),
	APPLICATION_CNRP_XML("application/cnrp+xml",null),
	APPLICATION_COMMONGROUND("application/commonground",null),
	APPLICATION_CPL_XML("application/cpl+xml",null),
	APPLICATION_CYBERCASH("application/cybercash",null),
	APPLICATION_DCA_RFT("application/dca-rft",null),
	APPLICATION_DEC_DX("application/dec-dx",null),
	APPLICATION_DVCS("application/dvcs",null),
	APPLICATION_EDI_CONSENT("application/edi-consent",null),
	APPLICATION_EDIFACT("application/edifact",null),
	APPLICATION_EDI_X12("application/edi-x12",null),
	APPLICATION_ESHOP("application/eshop",null),
	APPLICATION_FONT_TDPFR("application/font-tdpfr",null),
	APPLICATION_HTTP("application/http",null),
	APPLICATION_HYPERSTUDIO("application/hyperstudio",null),
	APPLICATION_IGES("application/iges",null),
	APPLICATION_INDEX("application/index",null),
	APPLICATION_INDEX_CMD("application/index.cmd",null),
	APPLICATION_INDEX_OBJ("application/index.obj",null),
	APPLICATION_INDEX_RESPONSE("application/index.response",null),
	APPLICATION_INDEX_VND("application/index.vnd",null),
	APPLICATION_IOTP("application/iotp",null),
	APPLICATION_IPP("application/ipp",null),
	APPLICATION_ISUP("application/isup",null),
	APPLICATION_MAC_BINHEX40("application/mac-binhex40",new String[]{"hqx"}),
	APPLICATION_MAC_COMPACTPRO("application/mac-compactpro",new String[]{"cpt"}),
	APPLICATION_MACWRITEII("application/macwriteii",null),
	APPLICATION_MARC("application/marc",null),
	APPLICATION_MATHEMATICA("application/mathematica",null),
	APPLICATION_MATHML_XML("application/mathml+xml",new String[]{"mathml"}),
	APPLICATION_MSWORD("application/msword",new String[]{"doc"}),
	APPLICATION_NEWS_MESSAGE_ID("application/news-message-id",null),
	APPLICATION_NEWS_TRANSMISSION("application/news-transmission",null),
	APPLICATION_OCSP_REQUEST("application/ocsp-request",null),
	APPLICATION_OCSP_RESPONSE("application/ocsp-response",null),
	APPLICATION_OCTET_STREAM("application/octet-stream",new String[]{"bin","dms","lha","lzh","exe","class","so","dll","dmg"}),
	APPLICATION_ODA("application/oda",new String[]{"oda"}),
	APPLICATION_OGG("application/ogg",new String[]{"ogg"}),
	APPLICATION_PARITYFEC("application/parityfec",null),
	APPLICATION_PDF("application/pdf",new String[]{"pdf"}),
	APPLICATION_PGP_ENCRYPTED("application/pgp-encrypted",null),
	APPLICATION_PGP_KEYS("application/pgp-keys",null),
	APPLICATION_PGP_SIGNATURE("application/pgp-signature",null),
	APPLICATION_PKCS10("application/pkcs10",null),
	APPLICATION_PKCS7_MIME("application/pkcs7-mime",null),
	APPLICATION_PKCS7_SIGNATURE("application/pkcs7-signature",null),
	APPLICATION_PKIX_CERT("application/pkix-cert",null),
	APPLICATION_PKIX_CRL("application/pkix-crl",null),
	APPLICATION_PKIXCMP("application/pkixcmp",null),
	APPLICATION_POSTSCRIPT("application/postscript",new String[]{"ai","eps","ps"}),
	APPLICATION_PRS_ALVESTRAND_TITRAX_SHEET("application/prs.alvestrand.titrax-sheet",null),
	APPLICATION_PRS_CWW("application/prs.cww",null),
	APPLICATION_PRS_NPREND("application/prs.nprend",null),
	APPLICATION_PRS_PLUCKER("application/prs.plucker",null),
	APPLICATION_QSIG("application/qsig",null),
	APPLICATION_RDF_XML("application/rdf+xml",new String[]{"rdf"}),
	APPLICATION_REGINFO_XML("application/reginfo+xml",null),
	APPLICATION_REMOTE_PRINTING("application/remote-printing",null),
	APPLICATION_RISCOS("application/riscos",null),
	APPLICATION_RTF("application/rtf",null),
	APPLICATION_SDP("application/sdp",null),
	APPLICATION_SET_PAYMENT("application/set-payment",null),
	APPLICATION_SET_PAYMENT_INITIATION("application/set-payment-initiation",null),
	APPLICATION_SET_REGISTRATION("application/set-registration",null),
	APPLICATION_SET_REGISTRATION_INITIATION("application/set-registration-initiation",null),
	APPLICATION_SGML("application/sgml",null),
	APPLICATION_SGML_OPEN_CATALOG("application/sgml-open-catalog",null),
	APPLICATION_SIEVE("application/sieve",null),
	APPLICATION_SLATE("application/slate",null),
	APPLICATION_SMIL("application/smil",new String[]{"smi","smil"}),
	APPLICATION_SRGS("application/srgs",new String[]{"gram"}),
	APPLICATION_SRGS_XML("application/srgs+xml",new String[]{"grxml"}),
	APPLICATION_TIMESTAMP_QUERY("application/timestamp-query",null),
	APPLICATION_TIMESTAMP_REPLY("application/timestamp-reply",null),
	APPLICATION_TVE_TRIGGER("application/tve-trigger",null),
	APPLICATION_VEMMI("application/vemmi",null),
	APPLICATION_VND_3GPP_PIC_BW_LARGE("application/vnd.3gpp.pic-bw-large",null),
	APPLICATION_VND_3GPP_PIC_BW_SMALL("application/vnd.3gpp.pic-bw-small",null),
	APPLICATION_VND_3GPP_PIC_BW_VAR("application/vnd.3gpp.pic-bw-var",null),
	APPLICATION_VND_3GPP_SMS("application/vnd.3gpp.sms",null),
	APPLICATION_VND_3M_POST_IT_NOTES("application/vnd.3m.post-it-notes",null),
	APPLICATION_VND_ACCPAC_SIMPLY_ASO("application/vnd.accpac.simply.aso",null),
	APPLICATION_VND_ACCPAC_SIMPLY_IMP("application/vnd.accpac.simply.imp",null),
	APPLICATION_VND_ACUCOBOL("application/vnd.acucobol",null),
	APPLICATION_VND_ACUCORP("application/vnd.acucorp",null),
	APPLICATION_VND_ADOBE_XFDF("application/vnd.adobe.xfdf",null),
	APPLICATION_VND_AETHER_IMP("application/vnd.aether.imp",null),
	APPLICATION_VND_AMIGA_AMI("application/vnd.amiga.ami",null),
	APPLICATION_VND_ANSER_WEB_CERTIFICATE_ISSUE_INITIATION("application/vnd.anser-web-certificate-issue-initiation",null),
	APPLICATION_VND_ANSER_WEB_FUNDS_TRANSFER_INITIATION("application/vnd.anser-web-funds-transfer-initiation",null),
	APPLICATION_VND_AUDIOGRAPH("application/vnd.audiograph",null),
	APPLICATION_VND_BLUEICE_MULTIPASS("application/vnd.blueice.multipass",null),
	APPLICATION_VND_BMI("application/vnd.bmi",null),
	APPLICATION_VND_BUSINESSOBJECTS("application/vnd.businessobjects",null),
	APPLICATION_VND_CANON_CPDL("application/vnd.canon-cpdl",null),
	APPLICATION_VND_CANON_LIPS("application/vnd.canon-lips",null),
	APPLICATION_VND_CINDERELLA("application/vnd.cinderella",null),
	APPLICATION_VND_CLAYMORE("application/vnd.claymore",null),
	APPLICATION_VND_COMMERCE_BATTELLE("application/vnd.commerce-battelle",null),
	APPLICATION_VND_COMMONSPACE("application/vnd.commonspace",null),
	APPLICATION_VND_CONTACT_CMSG("application/vnd.contact.cmsg",null),
	APPLICATION_VND_COSMOCALLER("application/vnd.cosmocaller",null),
	APPLICATION_VND_CRITICALTOOLS_WBS_XML("application/vnd.criticaltools.wbs+xml",null),
	APPLICATION_VND_CTC_POSML("application/vnd.ctc-posml",null),
	APPLICATION_VND_CUPS_POSTSCRIPT("application/vnd.cups-postscript",null),
	APPLICATION_VND_CUPS_RASTER("application/vnd.cups-raster",null),
	APPLICATION_VND_CUPS_RAW("application/vnd.cups-raw",null),
	APPLICATION_VND_CURL("application/vnd.curl",null),
	APPLICATION_VND_CYBANK("application/vnd.cybank",null),
	APPLICATION_VND_DATA_VISION_RDZ("application/vnd.data-vision.rdz",null),
	APPLICATION_VND_DNA("application/vnd.dna",null),
	APPLICATION_VND_DPGRAPH("application/vnd.dpgraph",null),
	APPLICATION_VND_DREAMFACTORY("application/vnd.dreamfactory",null),
	APPLICATION_VND_DXR("application/vnd.dxr",null),
	APPLICATION_VND_ECDIS_UPDATE("application/vnd.ecdis-update",null),
	APPLICATION_VND_ECOWIN_CHART("application/vnd.ecowin.chart",null),
	APPLICATION_VND_ECOWIN_FILEREQUEST("application/vnd.ecowin.filerequest",null),
	APPLICATION_VND_ECOWIN_FILEUPDATE("application/vnd.ecowin.fileupdate",null),
	APPLICATION_VND_ECOWIN_SERIES("application/vnd.ecowin.series",null),
	APPLICATION_VND_ECOWIN_SERIESREQUEST("application/vnd.ecowin.seriesrequest",null),
	APPLICATION_VND_ECOWIN_SERIESUPDATE("application/vnd.ecowin.seriesupdate",null),
	APPLICATION_VND_ENLIVEN("application/vnd.enliven",null),
	APPLICATION_VND_EPSON_ESF("application/vnd.epson.esf",null),
	APPLICATION_VND_EPSON_MSF("application/vnd.epson.msf",null),
	APPLICATION_VND_EPSON_QUICKANIME("application/vnd.epson.quickanime",null),
	APPLICATION_VND_EPSON_SALT("application/vnd.epson.salt",null),
	APPLICATION_VND_EPSON_SSF("application/vnd.epson.ssf",null),
	APPLICATION_VND_ERICSSON_QUICKCALL("application/vnd.ericsson.quickcall",null),
	APPLICATION_VND_EUDORA_DATA("application/vnd.eudora.data",null),
	APPLICATION_VND_FDF("application/vnd.fdf",null),
	APPLICATION_VND_FFSNS("application/vnd.ffsns",null),
	APPLICATION_VND_FINTS("application/vnd.fints",null),
	APPLICATION_VND_FLOGRAPHIT("application/vnd.flographit",null),
	APPLICATION_VND_FRAMEMAKER("application/vnd.framemaker",null),
	APPLICATION_VND_FSC_WEBLAUNCH("application/vnd.fsc.weblaunch",null),
	APPLICATION_VND_FUJITSU_OASYS("application/vnd.fujitsu.oasys",null),
	APPLICATION_VND_FUJITSU_OASYS2("application/vnd.fujitsu.oasys2",null),
	APPLICATION_VND_FUJITSU_OASYS3("application/vnd.fujitsu.oasys3",null),
	APPLICATION_VND_FUJITSU_OASYSGP("application/vnd.fujitsu.oasysgp",null),
	APPLICATION_VND_FUJITSU_OASYSPRS("application/vnd.fujitsu.oasysprs",null),
	APPLICATION_VND_FUJIXEROX_DDD("application/vnd.fujixerox.ddd",null),
	APPLICATION_VND_FUJIXEROX_DOCUWORKS("application/vnd.fujixerox.docuworks",null),
	APPLICATION_VND_FUJIXEROX_DOCUWORKS_BINDER("application/vnd.fujixerox.docuworks.binder",null),
	APPLICATION_VND_FUT_MISNET("application/vnd.fut-misnet",null),
	APPLICATION_VND_GRAFEQ("application/vnd.grafeq",null),
	APPLICATION_VND_GROOVE_ACCOUNT("application/vnd.groove-account",null),
	APPLICATION_VND_GROOVE_HELP("application/vnd.groove-help",null),
	APPLICATION_VND_GROOVE_IDENTITY_MESSAGE("application/vnd.groove-identity-message",null),
	APPLICATION_VND_GROOVE_INJECTOR("application/vnd.groove-injector",null),
	APPLICATION_VND_GROOVE_TOOL_MESSAGE("application/vnd.groove-tool-message",null),
	APPLICATION_VND_GROOVE_TOOL_TEMPLATE("application/vnd.groove-tool-template",null),
	APPLICATION_VND_GROOVE_VCARD("application/vnd.groove-vcard",null),
	APPLICATION_VND_HBCI("application/vnd.hbci",null),
	APPLICATION_VND_HHE_LESSON_PLAYER("application/vnd.hhe.lesson-player",null),
	APPLICATION_VND_HP_HPGL("application/vnd.hp-hpgl",null),
	APPLICATION_VND_HP_HPID("application/vnd.hp-hpid",null),
	APPLICATION_VND_HP_HPS("application/vnd.hp-hps",null),
	APPLICATION_VND_HP_PCL("application/vnd.hp-pcl",null),
	APPLICATION_VND_HP_PCLXL("application/vnd.hp-pclxl",null),
	APPLICATION_VND_HTTPHONE("application/vnd.httphone",null),
	APPLICATION_VND_HZN_3D_CROSSWORD("application/vnd.hzn-3d-crossword",null),
	APPLICATION_VND_IBM_AFPLINEDATA("application/vnd.ibm.afplinedata",null),
	APPLICATION_VND_IBM_ELECTRONIC_MEDIA("application/vnd.ibm.electronic-media",null),
	APPLICATION_VND_IBM_MINIPAY("application/vnd.ibm.minipay",null),
	APPLICATION_VND_IBM_MODCAP("application/vnd.ibm.modcap",null),
	APPLICATION_VND_IBM_RIGHTS_MANAGEMENT("application/vnd.ibm.rights-management",null),
	APPLICATION_VND_IBM_SECURE_CONTAINER("application/vnd.ibm.secure-container",null),
	APPLICATION_VND_INFORMIX_VISIONARY("application/vnd.informix-visionary",null),
	APPLICATION_VND_INTERCON_FORMNET("application/vnd.intercon.formnet",null),
	APPLICATION_VND_INTERTRUST_DIGIBOX("application/vnd.intertrust.digibox",null),
	APPLICATION_VND_INTERTRUST_NNCP("application/vnd.intertrust.nncp",null),
	APPLICATION_VND_INTU_QBO("application/vnd.intu.qbo",null),
	APPLICATION_VND_INTU_QFX("application/vnd.intu.qfx",null),
	APPLICATION_VND_IREPOSITORY_PACKAGE_XML("application/vnd.irepository.package+xml",null),
	APPLICATION_VND_IS_XPR("application/vnd.is-xpr",null),
	APPLICATION_VND_JAPANNET_DIRECTORY_SERVICE("application/vnd.japannet-directory-service",null),
	APPLICATION_VND_JAPANNET_JPNSTORE_WAKEUP("application/vnd.japannet-jpnstore-wakeup",null),
	APPLICATION_VND_JAPANNET_PAYMENT_WAKEUP("application/vnd.japannet-payment-wakeup",null),
	APPLICATION_VND_JAPANNET_REGISTRATION("application/vnd.japannet-registration",null),
	APPLICATION_VND_JAPANNET_REGISTRATION_WAKEUP("application/vnd.japannet-registration-wakeup",null),
	APPLICATION_VND_JAPANNET_SETSTORE_WAKEUP("application/vnd.japannet-setstore-wakeup",null),
	APPLICATION_VND_JAPANNET_VERIFICATION("application/vnd.japannet-verification",null),
	APPLICATION_VND_JAPANNET_VERIFICATION_WAKEUP("application/vnd.japannet-verification-wakeup",null),
	APPLICATION_VND_JISP("application/vnd.jisp",null),
	APPLICATION_VND_KDE_KARBON("application/vnd.kde.karbon",null),
	APPLICATION_VND_KDE_KCHART("application/vnd.kde.kchart",null),
	APPLICATION_VND_KDE_KFORMULA("application/vnd.kde.kformula",null),
	APPLICATION_VND_KDE_KIVIO("application/vnd.kde.kivio",null),
	APPLICATION_VND_KDE_KONTOUR("application/vnd.kde.kontour",null),
	APPLICATION_VND_KDE_KPRESENTER("application/vnd.kde.kpresenter",null),
	APPLICATION_VND_KDE_KSPREAD("application/vnd.kde.kspread",null),
	APPLICATION_VND_KDE_KWORD("application/vnd.kde.kword",null),
	APPLICATION_VND_KENAMEAAPP("application/vnd.kenameaapp",null),
	APPLICATION_VND_KOAN("application/vnd.koan",null),
	APPLICATION_VND_LIBERTY_REQUEST_XML("application/vnd.liberty-request+xml",null),
	APPLICATION_VND_LLAMAGRAPHICS_LIFE_BALANCE_DESKTOP("application/vnd.llamagraphics.life-balance.desktop",null),
	APPLICATION_VND_LLAMAGRAPHICS_LIFE_BALANCE_EXCHANGE_XML("application/vnd.llamagraphics.life-balance.exchange+xml",null),
	APPLICATION_VND_LOTUS_1_2_3("application/vnd.lotus-1-2-3",null),
	APPLICATION_VND_LOTUS_APPROACH("application/vnd.lotus-approach",null),
	APPLICATION_VND_LOTUS_FREELANCE("application/vnd.lotus-freelance",null),
	APPLICATION_VND_LOTUS_NOTES("application/vnd.lotus-notes",null),
	APPLICATION_VND_LOTUS_ORGANIZER("application/vnd.lotus-organizer",null),
	APPLICATION_VND_LOTUS_SCREENCAM("application/vnd.lotus-screencam",null),
	APPLICATION_VND_LOTUS_WORDPRO("application/vnd.lotus-wordpro",null),
	APPLICATION_VND_MCD("application/vnd.mcd",null),
	APPLICATION_VND_MEDIASTATION_CDKEY("application/vnd.mediastation.cdkey",null),
	APPLICATION_VND_MERIDIAN_SLINGSHOT("application/vnd.meridian-slingshot",null),
	APPLICATION_VND_MICROGRAFX_FLO("application/vnd.micrografx.flo",null),
	APPLICATION_VND_MICROGRAFX_IGX("application/vnd.micrografx.igx",null),
	APPLICATION_VND_MIF("application/vnd.mif",new String[]{"mif"}),
	APPLICATION_VND_MINISOFT_HP3000_SAVE("application/vnd.minisoft-hp3000-save",null),
	APPLICATION_VND_MITSUBISHI_MISTY_GUARD_TRUSTWEB("application/vnd.mitsubishi.misty-guard.trustweb",null),
	APPLICATION_VND_MOBIUS_DAF("application/vnd.mobius.daf",null),
	APPLICATION_VND_MOBIUS_DIS("application/vnd.mobius.dis",null),
	APPLICATION_VND_MOBIUS_MBK("application/vnd.mobius.mbk",null),
	APPLICATION_VND_MOBIUS_MQY("application/vnd.mobius.mqy",null),
	APPLICATION_VND_MOBIUS_MSL("application/vnd.mobius.msl",null),
	APPLICATION_VND_MOBIUS_PLC("application/vnd.mobius.plc",null),
	APPLICATION_VND_MOBIUS_TXF("application/vnd.mobius.txf",null),
	APPLICATION_VND_MOPHUN_APPLICATION("application/vnd.mophun.application",null),
	APPLICATION_VND_MOPHUN_CERTIFICATE("application/vnd.mophun.certificate",null),
	APPLICATION_VND_MOTOROLA_FLEXSUITE("application/vnd.motorola.flexsuite",null),
	APPLICATION_VND_MOTOROLA_FLEXSUITE_ADSI("application/vnd.motorola.flexsuite.adsi",null),
	APPLICATION_VND_MOTOROLA_FLEXSUITE_FIS("application/vnd.motorola.flexsuite.fis",null),
	APPLICATION_VND_MOTOROLA_FLEXSUITE_GOTAP("application/vnd.motorola.flexsuite.gotap",null),
	APPLICATION_VND_MOTOROLA_FLEXSUITE_KMR("application/vnd.motorola.flexsuite.kmr",null),
	APPLICATION_VND_MOTOROLA_FLEXSUITE_TTC("application/vnd.motorola.flexsuite.ttc",null),
	APPLICATION_VND_MOTOROLA_FLEXSUITE_WEM("application/vnd.motorola.flexsuite.wem",null),
	APPLICATION_VND_MOZILLA_XUL_XML("application/vnd.mozilla.xul+xml",new String[]{"xul"}),
	APPLICATION_VND_MS_ARTGALRY("application/vnd.ms-artgalry",null),
	APPLICATION_VND_MS_ASF("application/vnd.ms-asf",null),
	APPLICATION_VND_MS_EXCEL("application/vnd.ms-excel",new String[]{"xls"}),
	APPLICATION_VND_MS_LRM("application/vnd.ms-lrm",null),
	APPLICATION_VND_MS_POWERPOINT("application/vnd.ms-powerpoint",new String[]{"ppt"}),
	APPLICATION_VND_MS_PROJECT("application/vnd.ms-project",null),
	APPLICATION_VND_MS_TNEF("application/vnd.ms-tnef",null),
	APPLICATION_VND_MS_WORKS("application/vnd.ms-works",null),
	APPLICATION_VND_MS_WPL("application/vnd.ms-wpl",null),
	APPLICATION_VND_MSEQ("application/vnd.mseq",null),
	APPLICATION_VND_MSIGN("application/vnd.msign",null),
	APPLICATION_VND_MUSIC_NIFF("application/vnd.music-niff",null),
	APPLICATION_VND_MUSICIAN("application/vnd.musician",null),
	APPLICATION_VND_NETFPX("application/vnd.netfpx",null),
	APPLICATION_VND_NOBLENET_DIRECTORY("application/vnd.noblenet-directory",null),
	APPLICATION_VND_NOBLENET_SEALER("application/vnd.noblenet-sealer",null),
	APPLICATION_VND_NOBLENET_WEB("application/vnd.noblenet-web",null),
	APPLICATION_VND_NOVADIGM_EDM("application/vnd.novadigm.edm",null),
	APPLICATION_VND_NOVADIGM_EDX("application/vnd.novadigm.edx",null),
	APPLICATION_VND_NOVADIGM_EXT("application/vnd.novadigm.ext",null),
	APPLICATION_VND_OBN("application/vnd.obn",null),
	APPLICATION_VND_OSA_NETDEPLOY("application/vnd.osa.netdeploy",null),
	APPLICATION_VND_PALM("application/vnd.palm",null),
	APPLICATION_VND_PG_FORMAT("application/vnd.pg.format",null),
	APPLICATION_VND_PG_OSASLI("application/vnd.pg.osasli",null),
	APPLICATION_VND_POWERBUILDER6("application/vnd.powerbuilder6",null),
	APPLICATION_VND_POWERBUILDER6_S("application/vnd.powerbuilder6-s",null),
	APPLICATION_VND_POWERBUILDER7("application/vnd.powerbuilder7",null),
	APPLICATION_VND_POWERBUILDER7_S("application/vnd.powerbuilder7-s",null),
	APPLICATION_VND_POWERBUILDER75("application/vnd.powerbuilder75",null),
	APPLICATION_VND_POWERBUILDER75_S("application/vnd.powerbuilder75-s",null),
	APPLICATION_VND_PREVIEWSYSTEMS_BOX("application/vnd.previewsystems.box",null),
	APPLICATION_VND_PUBLISHARE_DELTA_TREE("application/vnd.publishare-delta-tree",null),
	APPLICATION_VND_PVI_PTID1("application/vnd.pvi.ptid1",null),
	APPLICATION_VND_PWG_MULTIPLEXED("application/vnd.pwg-multiplexed",null),
	APPLICATION_VND_PWG_XHTML_PRINT_XML("application/vnd.pwg-xhtml-print+xml",null),
	APPLICATION_VND_QUARK_QUARKXPRESS("application/vnd.quark.quarkxpress",null),
	APPLICATION_VND_RAPID("application/vnd.rapid",null),
	APPLICATION_VND_RN_REALMEDIA("application/vnd.rn-realmedia",new String[]{"rm"}),
	APPLICATION_VND_S3SMS("application/vnd.s3sms",null),
	APPLICATION_VND_SEALED_NET("application/vnd.sealed.net",null),
	APPLICATION_VND_SEEMAIL("application/vnd.seemail",null),
	APPLICATION_VND_SHANA_INFORMED_FORMDATA("application/vnd.shana.informed.formdata",null),
	APPLICATION_VND_SHANA_INFORMED_FORMTEMPLATE("application/vnd.shana.informed.formtemplate",null),
	APPLICATION_VND_SHANA_INFORMED_INTERCHANGE("application/vnd.shana.informed.interchange",null),
	APPLICATION_VND_SHANA_INFORMED_PACKAGE("application/vnd.shana.informed.package",null),
	APPLICATION_VND_SMAF("application/vnd.smaf",null),
	APPLICATION_VND_SSS_COD("application/vnd.sss-cod",null),
	APPLICATION_VND_SSS_DTF("application/vnd.sss-dtf",null),
	APPLICATION_VND_SSS_NTF("application/vnd.sss-ntf",null),
	APPLICATION_VND_STREET_STREAM("application/vnd.street-stream",null),
	APPLICATION_VND_SVD("application/vnd.svd",null),
	APPLICATION_VND_SWIFTVIEW_ICS("application/vnd.swiftview-ics",null),
	APPLICATION_VND_TRISCAPE_MXS("application/vnd.triscape.mxs",null),
	APPLICATION_VND_TRUEAPP("application/vnd.trueapp",null),
	APPLICATION_VND_TRUEDOC("application/vnd.truedoc",null),
	APPLICATION_VND_UFDL("application/vnd.ufdl",null),
	APPLICATION_VND_UPLANET_ALERT("application/vnd.uplanet.alert",null),
	APPLICATION_VND_UPLANET_ALERT_WBXML("application/vnd.uplanet.alert-wbxml",null),
	APPLICATION_VND_UPLANET_BEARER_CHOICE("application/vnd.uplanet.bearer-choice",null),
	APPLICATION_VND_UPLANET_BEARER_CHOICE_WBXML("application/vnd.uplanet.bearer-choice-wbxml",null),
	APPLICATION_VND_UPLANET_CACHEOP("application/vnd.uplanet.cacheop",null),
	APPLICATION_VND_UPLANET_CACHEOP_WBXML("application/vnd.uplanet.cacheop-wbxml",null),
	APPLICATION_VND_UPLANET_CHANNEL("application/vnd.uplanet.channel",null),
	APPLICATION_VND_UPLANET_CHANNEL_WBXML("application/vnd.uplanet.channel-wbxml",null),
	APPLICATION_VND_UPLANET_LIST("application/vnd.uplanet.list",null),
	APPLICATION_VND_UPLANET_LIST_WBXML("application/vnd.uplanet.list-wbxml",null),
	APPLICATION_VND_UPLANET_LISTCMD("application/vnd.uplanet.listcmd",null),
	APPLICATION_VND_UPLANET_LISTCMD_WBXML("application/vnd.uplanet.listcmd-wbxml",null),
	APPLICATION_VND_UPLANET_SIGNAL("application/vnd.uplanet.signal",null),
	APPLICATION_VND_VCX("application/vnd.vcx",null),
	APPLICATION_VND_VECTORWORKS("application/vnd.vectorworks",null),
	APPLICATION_VND_VIDSOFT_VIDCONFERENCE("application/vnd.vidsoft.vidconference",null),
	APPLICATION_VND_VISIO("application/vnd.visio",null),
	APPLICATION_VND_VISIONARY("application/vnd.visionary",null),
	APPLICATION_VND_VIVIDENCE_SCRIPTFILE("application/vnd.vividence.scriptfile",null),
	APPLICATION_VND_VSF("application/vnd.vsf",null),
	APPLICATION_VND_WAP_SIC("application/vnd.wap.sic",null),
	APPLICATION_VND_WAP_SLC("application/vnd.wap.slc",null),
	APPLICATION_VND_WAP_WBXML("application/vnd.wap.wbxml",new String[]{"wbxml"}),
	APPLICATION_VND_WAP_WMLC("application/vnd.wap.wmlc",new String[]{"wmlc"}),
	APPLICATION_VND_WAP_WMLSCRIPTC("application/vnd.wap.wmlscriptc",new String[]{"wmlsc"}),
	APPLICATION_VND_WEBTURBO("application/vnd.webturbo",null),
	APPLICATION_VND_WRQ_HP3000_LABELLED("application/vnd.wrq-hp3000-labelled",null),
	APPLICATION_VND_WT_STF("application/vnd.wt.stf",null),
	APPLICATION_VND_WV_CSP_WBXML("application/vnd.wv.csp+wbxml",null),
	APPLICATION_VND_XARA("application/vnd.xara",null),
	APPLICATION_VND_XFDL("application/vnd.xfdl",null),
	APPLICATION_VND_YAMAHA_HV_DIC("application/vnd.yamaha.hv-dic",null),
	APPLICATION_VND_YAMAHA_HV_SCRIPT("application/vnd.yamaha.hv-script",null),
	APPLICATION_VND_YAMAHA_HV_VOICE("application/vnd.yamaha.hv-voice",null),
	APPLICATION_VND_YELLOWRIVER_CUSTOM_MENU("application/vnd.yellowriver-custom-menu",null),
	APPLICATION_VOICEXML_XML("application/voicexml+xml",new String[]{"vxml"}),
	APPLICATION_WATCHERINFO_XML("application/watcherinfo+xml",null),
	APPLICATION_WHOISPP_QUERY("application/whoispp-query",null),
	APPLICATION_WHOISPP_RESPONSE("application/whoispp-response",null),
	APPLICATION_WITA("application/wita",null),
	APPLICATION_WORDPERFECT5_1("application/wordperfect5.1",null),
	APPLICATION_X_BCPIO("application/x-bcpio",new String[]{"bcpio"}),
	APPLICATION_X_CDLINK("application/x-cdlink",new String[]{"vcd"}),
	APPLICATION_X_CHESS_PGN("application/x-chess-pgn",new String[]{"pgn"}),
	APPLICATION_X_COMPRESS("application/x-compress",null),
	APPLICATION_X_CPIO("application/x-cpio",new String[]{"cpio"}),
	APPLICATION_X_CSH("application/x-csh",new String[]{"csh"}),
	APPLICATION_X_DIRECTOR("application/x-director",new String[]{"dcr","dir","dxr"}),
	APPLICATION_X_DVI("application/x-dvi",new String[]{"dvi"}),
	APPLICATION_X_FUTURESPLASH("application/x-futuresplash",new String[]{"spl"}),
	APPLICATION_X_GTAR("application/x-gtar",new String[]{"gtar"}),
	APPLICATION_X_GZIP("application/x-gzip",null),
	APPLICATION_X_HDF("application/x-hdf",new String[]{"hdf"}),
	APPLICATION_X_JAVASCRIPT("application/x-javascript",new String[]{"js"}),
	APPLICATION_X_KOAN("application/x-koan",new String[]{"skp","skd","skt","skm"}),
	APPLICATION_X_LATEX("application/x-latex",new String[]{"latex"}),
	APPLICATION_X_NETCDF("application/x-netcdf",new String[]{"nc","cdf"}),
	APPLICATION_X_SH("application/x-sh",new String[]{"sh"}),
	APPLICATION_X_SHAR("application/x-shar",new String[]{"shar"}),
	APPLICATION_X_SHOCKWAVE_FLASH("application/x-shockwave-flash",new String[]{"swf"}),
	APPLICATION_X_STUFFIT("application/x-stuffit",new String[]{"sit"}),
	APPLICATION_X_SV4CPIO("application/x-sv4cpio",new String[]{"sv4cpio"}),
	APPLICATION_X_SV4CRC("application/x-sv4crc",new String[]{"sv4crc"}),
	APPLICATION_X_TAR("application/x-tar",new String[]{"tar"}),
	APPLICATION_X_TCL("application/x-tcl",new String[]{"tcl"}),
	APPLICATION_X_TEX("application/x-tex",new String[]{"tex"}),
	APPLICATION_X_TEXINFO("application/x-texinfo",new String[]{"texinfo","texi"}),
	APPLICATION_X_TROFF("application/x-troff",new String[]{"t","tr","roff"}),
	APPLICATION_X_TROFF_MAN("application/x-troff-man",new String[]{"man"}),
	APPLICATION_X_TROFF_ME("application/x-troff-me",new String[]{"me"}),
	APPLICATION_X_TROFF_MS("application/x-troff-ms",new String[]{"ms"}),
	APPLICATION_X_USTAR("application/x-ustar",new String[]{"ustar"}),
	APPLICATION_X_WAIS_SOURCE("application/x-wais-source",new String[]{"src"}),
	APPLICATION_X400_BP("application/x400-bp",null),
	APPLICATION_XHTML_XML("application/xhtml+xml",new String[]{"xhtml","xht"}),
	APPLICATION_XSLT_XML("application/xslt+xml",new String[]{"xslt"}),
	APPLICATION_XML("application/xml",new String[]{"xml","xsl"}),
	APPLICATION_XML_DTD("application/xml-dtd",new String[]{"dtd"}),
	APPLICATION_XML_EXTERNAL_PARSED_ENTITY("application/xml-external-parsed-entity",null),
	APPLICATION_ZIP("application/zip",new String[]{"zip"}),
	APPLICATION_X_HTTPD_PHP("application/x-httpd-php",new String[]{"php"}),
	APPLICATION_X_HTTPD_PHP_SOURCE("application/x-httpd-php-source",new String[]{"phps"}),
	AUDIO_32KADPCM("audio/32kadpcm",null),
	AUDIO_AMR("audio/amr",null),
	AUDIO_AMR_WB("audio/amr-wb",null),
	AUDIO_BASIC("audio/basic",new String[]{"au","snd"}),
	AUDIO_CN("audio/cn",null),
	AUDIO_DAT12("audio/dat12",null),
	AUDIO_DSR_ES201108("audio/dsr-es201108",null),
	AUDIO_DVI4("audio/dvi4",null),
	AUDIO_EVRC("audio/evrc",null),
	AUDIO_EVRC0("audio/evrc0",null),
	AUDIO_G722("audio/g722",null),
	AUDIO_G_722_1("audio/g.722.1",null),
	AUDIO_G723("audio/g723",null),
	AUDIO_G726_16("audio/g726-16",null),
	AUDIO_G726_24("audio/g726-24",null),
	AUDIO_G726_32("audio/g726-32",null),
	AUDIO_G726_40("audio/g726-40",null),
	AUDIO_G728("audio/g728",null),
	AUDIO_G729("audio/g729",null),
	AUDIO_G729D("audio/g729D",null),
	AUDIO_G729E("audio/g729E",null),
	AUDIO_GSM("audio/gsm",null),
	AUDIO_GSM_EFR("audio/gsm-efr",null),
	AUDIO_L8("audio/l8",null),
	AUDIO_L16("audio/l16",null),
	AUDIO_L20("audio/l20",null),
	AUDIO_L24("audio/l24",null),
	AUDIO_LPC("audio/lpc",null),
	AUDIO_MIDI("audio/midi",new String[]{"mid","midi","kar"}),
	AUDIO_MPA("audio/mpa",null),
	AUDIO_MPA_ROBUST("audio/mpa-robust",null),
	AUDIO_MP4A_LATM("audio/mp4a-latm",null),
	AUDIO_MPEG("audio/mpeg",new String[]{"mpga","mp2","mp3"}),
	AUDIO_PARITYFEC("audio/parityfec",null),
	AUDIO_PCMA("audio/pcma",null),
	AUDIO_PCMU("audio/pcmu",null),
	AUDIO_PRS_SID("audio/prs.sid",null),
	AUDIO_QCELP("audio/qcelp",null),
	AUDIO_RED("audio/red",null),
	AUDIO_SMV("audio/smv",null),
	AUDIO_SMV0("audio/smv0",null),
	AUDIO_TELEPHONE_EVENT("audio/telephone-event",null),
	AUDIO_TONE("audio/tone",null),
	AUDIO_VDVI("audio/vdvi",null),
	AUDIO_VND_3GPP_IUFP("audio/vnd.3gpp.iufp",null),
	AUDIO_VND_CISCO_NSE("audio/vnd.cisco.nse",null),
	AUDIO_VND_CNS_ANP1("audio/vnd.cns.anp1",null),
	AUDIO_VND_CNS_INF1("audio/vnd.cns.inf1",null),
	AUDIO_VND_DIGITAL_WINDS("audio/vnd.digital-winds",null),
	AUDIO_VND_EVERAD_PLJ("audio/vnd.everad.plj",null),
	AUDIO_VND_LUCENT_VOICE("audio/vnd.lucent.voice",null),
	AUDIO_VND_NORTEL_VBK("audio/vnd.nortel.vbk",null),
	AUDIO_VND_NUERA_ECELP4800("audio/vnd.nuera.ecelp4800",null),
	AUDIO_VND_NUERA_ECELP7470("audio/vnd.nuera.ecelp7470",null),
	AUDIO_VND_NUERA_ECELP9600("audio/vnd.nuera.ecelp9600",null),
	AUDIO_VND_OCTEL_SBC("audio/vnd.octel.sbc",null),
	AUDIO_VND_QCELP("audio/vnd.qcelp",null),
	AUDIO_VND_RHETOREX_32KADPCM("audio/vnd.rhetorex.32kadpcm",null),
	AUDIO_VND_VMX_CVSD("audio/vnd.vmx.cvsd",null),
	AUDIO_X_AIFF("audio/x-aiff",new String[]{"aif","aiff","aifc"}),
	AUDIO_X_ALAW_BASIC("audio/x-alaw-basic",null),
	AUDIO_X_MPEGURL("audio/x-mpegurl",new String[]{"m3u"}),
	AUDIO_X_PN_REALAUDIO("audio/x-pn-realaudio",new String[]{"ram","ra"}),
	AUDIO_X_PN_REALAUDIO_PLUGIN("audio/x-pn-realaudio-plugin",null),
	AUDIO_X_WAV("audio/x-wav",new String[]{"wav"}),
	CHEMICAL_X_PDB("chemical/x-pdb",new String[]{"pdb"}),
	CHEMICAL_X_XYZ("chemical/x-xyz",new String[]{"xyz"}),
	IMAGE_TIFF_FX("image/tiff-fx",null),
	IMAGE_VND_CNS_INF2("image/vnd.cns.inf2",null),
	IMAGE_VND_DJVU("image/vnd.djvu",new String[]{"djvu","djv"}),
	IMAGE_VND_DWG("image/vnd.dwg",null),
	IMAGE_VND_DXF("image/vnd.dxf",null),
	IMAGE_VND_FASTBIDSHEET("image/vnd.fastbidsheet",null),
	IMAGE_VND_FPX("image/vnd.fpx",null),
	IMAGE_VND_FST("image/vnd.fst",null),
	IMAGE_VND_FUJIXEROX_EDMICS_MMR("image/vnd.fujixerox.edmics-mmr",null),
	IMAGE_VND_FUJIXEROX_EDMICS_RLC("image/vnd.fujixerox.edmics-rlc",null),
	IMAGE_VND_GLOBALGRAPHICS_PGB("image/vnd.globalgraphics.pgb",null),
	IMAGE_VND_MIX("image/vnd.mix",null),
	IMAGE_VND_MS_MODI("image/vnd.ms-modi",null),
	IMAGE_VND_NET_FPX("image/vnd.net-fpx",null),
	IMAGE_VND_SVF("image/vnd.svf",null),
	IMAGE_VND_WAP_WBMP("image/vnd.wap.wbmp",new String[]{"wbmp"}),
	IMAGE_VND_XIFF("image/vnd.xiff",null),
	IMAGE_X_CMU_RASTER("image/x-cmu-raster",new String[]{"ras"}),
	IMAGE_X_ICON("image/x-icon",new String[]{"ico"}),
	IMAGE_X_PORTABLE_ANYMAP("image/x-portable-anymap",new String[]{"pnm"}),
	IMAGE_X_PORTABLE_BITMAP("image/x-portable-bitmap",new String[]{"pbm"}),
	IMAGE_X_PORTABLE_GRAYMAP("image/x-portable-graymap",new String[]{"pgm"}),
	IMAGE_X_PORTABLE_PIXMAP("image/x-portable-pixmap",new String[]{"ppm"}),
	IMAGE_X_RGB("image/x-rgb",new String[]{"rgb"}),
	IMAGE_X_XBITMAP("image/x-xbitmap",new String[]{"xbm"}),
	IMAGE_X_XPIXMAP("image/x-xpixmap",new String[]{"xpm"}),
	IMAGE_X_XWINDOWDUMP("image/x-xwindowdump",new String[]{"xwd"}),
	MESSAGE_DELIVERY_STATUS("message/delivery-status",null),
	MESSAGE_DISPOSITION_NOTIFICATION("message/disposition-notification",null),
	MESSAGE_EXTERNAL_BODY("message/external-body",null),
	MESSAGE_HTTP("message/http",null),
	MESSAGE_NEWS("message/news",null),
	MESSAGE_PARTIAL("message/partial",null),
	MESSAGE_RFC822("message/rfc822",null),
	MESSAGE_S_HTTP("message/s-http",null),
	MESSAGE_SIP("message/sip",null),
	MESSAGE_SIPFRAG("message/sipfrag",null),
	MODEL_IGES("model/iges",new String[]{"igs","iges"}),
	MODEL_MESH("model/mesh",new String[]{"msh","mesh","silo"}),
	MODEL_VND_DWF("model/vnd.dwf",null),
	MODEL_VND_FLATLAND_3DML("model/vnd.flatland.3dml",null),
	MODEL_VND_GDL("model/vnd.gdl",null),
	MODEL_VND_GS_GDL("model/vnd.gs-gdl",null),
	MODEL_VND_GTW("model/vnd.gtw",null),
	MODEL_VND_MTS("model/vnd.mts",null),
	MODEL_VND_PARASOLID_TRANSMIT_BINARY("model/vnd.parasolid.transmit.binary",null),
	MODEL_VND_PARASOLID_TRANSMIT_TEXT("model/vnd.parasolid.transmit.text",null),
	MODEL_VND_VTU("model/vnd.vtu",null),
	MODEL_VRML("model/vrml",new String[]{"wrl","vrml"}),
	MULTIPART_ALTERNATIVE("multipart/alternative",null),
	MULTIPART_APPLEDOUBLE("multipart/appledouble",null),
	MULTIPART_BYTERANGES("multipart/byteranges",null),
	MULTIPART_DIGEST("multipart/digest",null),
	MULTIPART_ENCRYPTED("multipart/encrypted",null),
	MULTIPART_FORM_DATA("multipart/form-data",null),
	MULTIPART_HEADER_SET("multipart/header-set",null),
	MULTIPART_MIXED("multipart/mixed",null),
	MULTIPART_PARALLEL("multipart/parallel",null),
	MULTIPART_RELATED("multipart/related",null),
	MULTIPART_REPORT("multipart/report",null),
	MULTIPART_SIGNED("multipart/signed",null),
	MULTIPART_VOICE_MESSAGE("multipart/voice-message",null),
	VIDEO_BMPEG("video/bmpeg",null),
	VIDEO_BT656("video/bt656",null),
	VIDEO_CELB("video/celb",null),
	VIDEO_DV("video/dv",null),
	VIDEO_H261("video/h261",null),
	VIDEO_H263("video/h263",null),
	VIDEO_H263_1998("video/h263-1998",null),
	VIDEO_H263_3500("video/h263-3500",null),
	VIDEO_JPEG("video/jpeg",null),
	VIDEO_MP1S("video/mp1s",null),
	VIDEO_MP2P("video/mp2p",null),
	VIDEO_MP2T("video/mp2t",null),
	VIDEO_MP4V_ES("video/mp4v-es",null),
	VIDEO_MPV("video/mpv",null),
	VIDEO_MPEG("video/mpeg",new String[]{"mpeg","mpg","mpe"}),
	VIDEO_NV("video/nv",null),
	VIDEO_PARITYFEC("video/parityfec",null),
	VIDEO_POINTER("video/pointer",null),
	VIDEO_QUICKTIME("video/quicktime",new String[]{"qt","mov"}),
	VIDEO_SMPTE292M("video/smpte292m",null),
	VIDEO_VND_FVT("video/vnd.fvt",null),
	VIDEO_VND_MOTOROLA_VIDEO("video/vnd.motorola.video",null),
	VIDEO_VND_MOTOROLA_VIDEOP("video/vnd.motorola.videop",null),
	VIDEO_VND_MPEGURL("video/vnd.mpegurl",new String[]{"mxu","m4u"}),
	VIDEO_VND_NOKIA_INTERLEAVED_MULTIMEDIA("video/vnd.nokia.interleaved-multimedia",null),
	VIDEO_VND_OBJECTVIDEO("video/vnd.objectvideo",null),
	VIDEO_VND_VIVO("video/vnd.vivo",null),
	VIDEO_X_MSVIDEO("video/x-msvideo",new String[]{"avi"}),
	VIDEO_X_SGI_MOVIE("video/x-sgi-movie",new String[]{"movie"}),
	X_CONFERENCE_X_COOLTALK("x-conference/x-cooltalk",new String[]{"ice"}),
	;
 
 	private String typeStr;
 
	private String type;
	
	private String subType;
	
	private String[] extensions;
	
	private String defaultExtension;
	
	private ContentsType(String typeStr, String[] subTypeList) {
		this(typeStr, subTypeList, "");
	}
	
	private ContentsType(String typeStr, String[] subTypeList, String defaultExtension) {
		this.typeStr          = typeStr;
		String[] typeList     = typeStr.split("/");
		this.type             = typeList[0];
		this.subType          = typeList[1];
		this.extensions       = subTypeList;
		this.defaultExtension = defaultExtension;
	}
	
	/**
	 * 指定されたコンテンツタイプがこのコンテンツタイプと合致するか判定する。<br/>
	 * 
	 * 引数に指定されたコンテンツタイプに文字コード指定等その他文字列が含まれていても判定することができます。
	 * 
	 * @param contentType コンテンツタイプ文字列
	 * @return 判定結果
	 */
	public boolean isType(String contentType) {
		if (contentType.indexOf(this.typeStr) == -1) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * コンテンツタイプを取得します。<br/>
	 * 
	 * text/html と定義されていた場合、textを返却します。
	 * 
	 * @return コンテンツタイプ文字列
	 */
	public String getType() {
		return this.type;
	}
	
	/**
	 * コンテンツサブタイプを取得します。<br/>
	 * 
	 * text/html と定義されていた場合、htmlを返却します。
	 * 
	 * @return コンテンツサブタイプ文字列
	 */
	public String getSubType() {
		return this.subType;
	}
	
	/**
	 * このコンテンツタイプに属する拡張子一覧を取得する。<br/>
	 * 
	 * 拡張子が定義されていない場合、nullを返却する。
	 * 
	 * @return 拡張子一覧
	 */
	public String[] getExtension() {
		if (this.extensions == null) {
			return null;
		} else {
			return (String[])this.extensions.clone();
		}
	}

	/**
	 * このコンテンツタイプのデフォルト拡張子を取得する。<br/>
	 * 
	 * 拡張子が定義されていない場合、空文字を返却する。
	 * 
	 * @return デフォルト拡張子
	 */
	public String getDefaultExtension() {
		if (this.extensions == null || this.extensions.length == 0) return "";
		return this.extensions[0];
	}
	
	/**
	 * このコンテンツタイプに属する拡張子一覧に指定の拡張子を保持するか判定する。<br/>
	 * 
	 * @param extension 拡張子
	 * @return 判定結果(true=拡張子を含む, false=拡張子を含まない)
	 */
	public boolean hasExtension(String extension) {
		if (this.extensions == null) {
			return false;
		} else {
			for (String extensionStr : this.extensions) {
				if (extensionStr.equals(extension)) {
					return true;
				}
			}
			return false;
		}
	}
}
