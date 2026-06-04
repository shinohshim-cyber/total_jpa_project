import "./App.css";
import Table from "react-bootstrap/Table";
import Pagination from "react-bootstrap/Pagination";
import api from "./api";
import { use, useEffect, useState } from "react";

function App() {
  //  데이터 가져와서 사용할 state
  const [users, setUsers] = useState([]);

  //  현재 페이지 번호 스테이트
  const [page, setPage] = useState(0);
  //  전체 페이지 수를 저장 스테이트(페이징 번호)

  //  처음 페이지가 로딩되면 DB에서 api 요청하기
  const [totalPage, setTotalPage] = useState(0);

  //  localhost:800/api
  useEffect(() => {
    api
      .get(`/getPage?page=${page}&size=10`)
      .then((res) => {
        console.log(res.data);
        setUsers(res.data.content);
        //  전체 페이지 할당
        setTotalPage(res.data.totalPages);
      })
      .catch((err) => {
        console.log(err);
      });
  }, [page]); // page 값이 바뀔 때 마다 실행

  //  한 화면에 10개 페이지씩 보여주기
  //  0 ~ 9 페이지 : 0 그룹 (1 .... 10)
  //  10 ~ 19 페이지 : 1 그룹 ( 11 .... 20)
  //  마지막 그룹 : 마지막 페이지와 전체페이지수 중 작은 값 선택
  const pageGroup = Math.floor(page / 10);
  const startPage = pageGroup * 10;
  const endPage = Math.min(startPage + 10, totalPage);

  return (
    <div className="container py-5">
      <div className="text-center mb-5">
        <h1 className="display-4 fw-bold text-primary">User 목록</h1>
        <p className="text-secondary">
          Sping Boot + React + JPA Sample Project
        </p>
      </div>
      <Table striped bordered hover>
        <thead>
          <tr>
            <th>#</th>
            <th>Name</th>
            <th>email</th>
            <th>LikeColor</th>
            <th>생성일</th>
            <th>수정일</th>
          </tr>
        </thead>
        <tbody>
          {users.map((user) => {
            return (
              <tr key={user.id}>
                <td>{user.id}</td>
                <td>{user.name}</td>
                <td>{user.email}</td>
                <td>{user.likeColor}</td>
                <td>{new Date(user.createdAt).toLocaleString("ko-KR")}</td>
                <td>{new Date(user.updatedAt).toLocaleString("ko-KR")}</td>
              </tr>
            );
          })}
        </tbody>
      </Table>
      {/* 페이지 네이션 시작 */}
      <div className="d-flex justify-content-center mt-4">
        <Pagination>
          <Pagination.First
            //  page = 0 이면 disabled = true
            disabled={page < 10}
            onClick={() => {
              if (page > 10) setPage(page - 10);
            }}
          />
          <Pagination.Prev
            disabled={page == 0}
            onClick={() => {
              if (page > 0) setPage(page - 1);
            }}
          />
          {[...Array(endPage - startPage)].map((_, index) => {
            const pageNumber = startPage + index;
            return (
              <Pagination.Item
                key={pageNumber}
                active={page == pageNumber}
                onClick={() => setPage(pageNumber)}
              >
                {pageNumber + 1}
              </Pagination.Item>
            );
          })}
          <Pagination.Next
            disabled={page == totalPage - 1}
            onClick={() => {
              if (page < totalPage - 1) setPage(page + 1);
            }}
          />
          <Pagination.Last
            disabled={page >= totalPage - 10}
            onClick={() => {
              if (page < totalPage - 10) setPage(page + 10);
            }}
          />
        </Pagination>
      </div>
      {/* 페이지 네이션 종료 */}
    </div>
  );
}

export default App;
