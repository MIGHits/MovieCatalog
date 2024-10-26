package com.example.moviecatalog.data.data.mappers

import com.example.moviecatalog.data.data.remote.entities.PageInfoModelDTO
import com.example.moviecatalog.domain.entity.PageInfoModel

class PageInfoMapper {
    fun map(pageDTO:PageInfoModelDTO):PageInfoModel{
        return PageInfoModel(
            pageSize = pageDTO.pageSize,
            pageCount = pageDTO.pageCount,
            currentPage = pageDTO.currentPage
        )
    }
}