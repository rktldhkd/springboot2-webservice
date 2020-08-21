#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

function switch_proxy() {
  IDLE_PORT=$(find_idle_port)

  echo "> 전환할 Port: $IDLE_PORT"
  echo "> Port 전환"
  echo "set \$service_url http://127.0.0.1:${IDLE_PORT};" | sudo tee /etc/nginx/conf.d/service-url.inc
  # 하나의 문장을만드어 파이프라인(|)으로 넘겨주기위해 echo 사용
  # 앞에서 넘긴 set이하의 문장 결과를 serice-url.inc에 덮어씌운다.

  echo "> 엔진엑스 Reload 시작"
  sudo service nginx reload
  # reload 와 restart는 다르다.
  # restart는 잠시 끊기는 현상이 있지만 중요한 설정들을 반영할때 restart 사용.
  # 여기선 외부의 설정 파일인 service-url을 다시 불러오는거라 reload로 가능하다.
}