package com.fuzhutech.ueditor;

import com.baidu.ueditor.define.ActionMap;
import com.baidu.ueditor.define.AppInfo;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.State;
import com.baidu.ueditor.hunter.ImageHunter;
import com.fuzhutech.ueditor.hunter.FileManager;
import com.fuzhutech.ueditor.upload.Uploader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author     : fuzhu
 * @group      : fuzhutech
 * @Date       : 2017-05-24 21:14:49
 * @Comments   : 扩展原有功能
 * @Version    : 1.0.0
 */
public class ActionEnter extends com.baidu.ueditor.ActionEnter{

    public ActionEnter (HttpServletRequest request, String rootPath, String configFilePath ) {
        this.request = request;
        this.rootPath = rootPath;
        this.actionType = request.getParameter( "action" );
        this.contextPath = request.getContextPath();

        this.configManager = ConfigManager.getInstance( rootPath, this.contextPath, request.getRequestURI(),configFilePath);
    }

    @Override
    public String invoke() {

        if ( actionType == null || !ActionMap.mapping.containsKey( actionType ) ) {
            return new BaseState( false, AppInfo.INVALID_ACTION ).toJSONString();
        }

        if ( this.configManager == null){
        }

        if ( this.configManager == null || !this.configManager.valid() ) {
            return new BaseState( false, AppInfo.CONFIG_ERROR ).toJSONString();
        }

        State state = null;

        int actionCode = ActionMap.getType( this.actionType );

        Map<String, Object> conf = null;

        switch ( actionCode ) {

            /*case ActionMap.CONFIG:
                return this.configManager.getAllConfig().toString();*/

            case ActionMap.UPLOAD_IMAGE:
            case ActionMap.UPLOAD_SCRAWL:
            case ActionMap.UPLOAD_VIDEO:
            case ActionMap.UPLOAD_FILE:
                conf = this.configManager.getConfig( actionCode );
                state = new Uploader( request, conf ).doExec();
                break;

            /*case ActionMap.CATCH_IMAGE:
                conf = configManager.getConfig( actionCode );
                String[] list = this.request.getParameterValues( (String)conf.get( "fieldName" ) );
                state = new ImageHunter( conf ).capture( list );
                break;*/

            case ActionMap.LIST_IMAGE:
            case ActionMap.LIST_FILE:
                conf = configManager.getConfig( actionCode );
                int start = this.getStartIndex();
                state = new FileManager( conf ).listFile( start );
                break;
            default:return super.invoke();
        }

        return state.toJSONString();

    }
}
