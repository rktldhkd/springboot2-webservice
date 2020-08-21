#!/usr/bin/env bash

# 쉬고있는 profile찾기: real1이 사용중이면 real2가 쉬고, 반대면 real1이 쉬고있음.
function find_idle_profile()
{
  #현재 nginx가 보고있는 프록시/스프링부트가 정상적으로 수행중인지 확인.
  RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost/profile)

  if [ ${RESPONSE_CODE} -ge 400 ] #400보다 크면, 즉 40x/50x에러 모두 포함
  then
      CURRENT_PROFILE=real2
  else
      CURRENT_PROFILE=$(curl -s http://localhost/profile)
  fi

  if [ ${CURRENT_PROFILE} == real1 ]
  then
    IDLE_PROFILE=real2
  else
    IDLE_PROFILE=real1
  fi

  #bash라는 스크립트는 값반환기능이 없다. 대용으로 echo를 사용.
  echo "${IDLE_PROFILE}"
}

# 쉬고있는 profile의 port 찾기
function find_idle_port()
{
    IDLE_PROFILE=$(find_idle_profile)

    if [ ${IDLE_PROFILE} == real1 ]
    then
      echo "8081"
    else
      echo "8082"
    fi
}