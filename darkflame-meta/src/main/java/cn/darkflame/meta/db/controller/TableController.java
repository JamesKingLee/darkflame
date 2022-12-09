package cn.darkflame.meta.db.controller;


import cn.darkflame.meta.db.service.ITableService;
import org.springframework.web.bind.annotation.*;

//import javax.annotation.Resource;
import java.util.Map;

/**
 * @author james
 */
@RestController
@CrossOrigin
@RequestMapping("/table")
public class TableController {

//    @Resource
    private ITableService tableService;

    @PostMapping("/create")
    public String create(@RequestPart String tblName, @RequestPart Map<String, String> colInfo) {
        return tableService.createTbl(tblName, colInfo);
    }

}
