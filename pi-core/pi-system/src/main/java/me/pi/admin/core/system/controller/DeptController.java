/*
 * Copyright 2022-2023 ZnPi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.pi.admin.core.system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import me.pi.admin.core.log.annotation.Log;
import me.pi.admin.core.system.service.DeptService;
import me.pi.admin.core.system.pojo.dto.DeptDTO;
import me.pi.admin.core.system.pojo.query.DeptTreeQuery;
import me.pi.admin.core.system.pojo.vo.DeptTreeVO;
import me.pi.admin.common.pojo.vo.SelectTreeVO;
import me.pi.admin.common.pojo.vo.ResponseData;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author ZnPi
 * @since 2022-09-04
 */
@RestController
@RequestMapping("/dept")
@Tag(name = "部门管理")
@RequiredArgsConstructor
@SecurityRequirement(name = HttpHeaders.AUTHORIZATION)
public class DeptController {
    private final DeptService deptService;

    @GetMapping
    @Operation(summary = "获取部门（树形）")
    @PreAuthorize("hasAuthority('sys_dept_query')")
    public ResponseData<List<DeptTreeVO>> getDeptTree(DeptTreeQuery query) {
        return ResponseData.ok(deptService.getDeptTree(query));
    }

    @PostMapping
    @Operation(summary = "新增部门")
    @PreAuthorize("hasAuthority('sys_dept_add')")
    public ResponseData<?> saveDept(@RequestBody @Valid DeptDTO dto) {
        deptService.saveOrUpdate(dto);
        return ResponseData.ok();
    }

    @PutMapping
    @Operation(summary = "编辑部门")
    @PreAuthorize("hasAuthority('sys_dept_update')")
    @Log("编辑部门")
    public ResponseData<?> updateDept(@RequestBody @Valid DeptDTO dto) {
        deptService.saveOrUpdate(dto);
        return ResponseData.ok();
    }

    @DeleteMapping("/{ids}")
    @Operation(summary = "删除部门")
    @PreAuthorize("hasAuthority('sys_dept_delete')")
    public ResponseData<?> deleteDept(@PathVariable String ids){
        deptService.deleteDept(ids);
        return ResponseData.ok();
    }

    @Operation(summary = "获取部门选择器（树形）")
    @GetMapping("/deptSelectTree")
    public ResponseData<List<SelectTreeVO>> getDeptSelectTree() {
        return ResponseData.ok(deptService.getDeptSelectTree());
    }
}
