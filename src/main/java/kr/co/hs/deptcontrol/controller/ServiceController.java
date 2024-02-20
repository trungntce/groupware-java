package kr.co.hs.deptcontrol.controller;

import kr.co.hs.deptcontrol.dto.DeptControlDTO;
import kr.co.hs.deptcontrol.service.DragDropService;
import kr.co.hs.emp.dto.EmpDTO;
import kr.co.hs.emp.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@RestController
public class ServiceController {
    @Autowired
    private DragDropService dragDropService;

    @Autowired
    private EmpService empService;

    @GetMapping(value = "/api/findAllDeptList", produces = "application/json; charset=UTF-8")
    public Object findAllDeptList(DeptControlDTO deptControlDTO){
        String langCd = LocaleContextHolder.getLocale().toString();
        return dragDropService.getTreeDept(langCd);
    }

    @RequestMapping(value = "/api/listenDragDrop", method = RequestMethod.POST)
    @ResponseBody
    public String listenDragDrop(@RequestBody List<DeptControlDTO> editList, HttpServletRequest request) {
        String langCd = LocaleContextHolder.getLocale().toString();
        String result=null;
        EmpDTO emp = empService.getSessionUserLogin(request);

        HashMap par = new HashMap();
        par.put("empCd", emp.getEmpCd());

        int plNumber = dragDropService.getPositionLevelNumber(par);
        System.out.println("DXD: "+plNumber);
        if (plNumber<=700){
            List<DeptControlDTO> initList= dragDropService.getTreeDept(langCd);
            result = "It's not OKAY";
            for (int i = 0; i < editList.size(); i++) {
                for (int j=0;j<initList.size();j++){
                    if(editList.get(i).getPublicCd().equals(initList.get(j).getPublicCd()) && !editList.get(i).getPublicParentCd().equals(initList.get(j).getPublicParentCd()) && editList.get(i).getPublicParentCd().split("_")[0].equals("E")){
                        result="Parent is not Dept";
                        break;
                    }
                    //check chinh
                    if(editList.get(i).getPublicCd().equals(initList.get(j).getPublicCd()) && !editList.get(i).getPublicParentCd().equals(initList.get(j).getPublicParentCd()) && editList.get(i).getPublicParentCd().split("_")[0].equals("D")){
                        //update o day

                        if(editList.get(i).getPublicCd().split("_")[0].equals("D")){
                            //update o bang department
                            HashMap param = new HashMap();
                            param.put("deptCd",editList.get(i).getPublicCd().split("_")[1]);
                            if(editList.get(i).getPublicParentCd().equals("0")){
                                param.put("deptParentCd", null);
                                result = "It's OKAY";
                            }else{
                                param.put("deptParentCd", editList.get(i).getPublicParentCd().split("_")[1]);
                                result = "It's OKAY";
                            }
                            dragDropService.upDeptTree(param);
                        }else{
                            //update o bang emp
                            HashMap param = new HashMap();
                            param.put("empCd",editList.get(i).getPublicCd().split("_")[1]);
                            if(editList.get(i).getPublicParentCd().equals("0")){
                                param.put("deptCd",null);
                                result = "It's OKAY";
                            }else{
                                param.put("deptCd",editList.get(i).getPublicParentCd().split("_")[1]);
                                result = "It's OKAY";
                            }
                            dragDropService.upEmpTree(param);
                        }
                        break;
                    }
                }
            }
        }else {
            result = "permission";
        }
        return result;
    }
}