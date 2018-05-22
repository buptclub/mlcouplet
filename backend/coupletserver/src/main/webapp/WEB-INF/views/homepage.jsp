<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="/css/pure-min.css">
    <link rel="stylesheet" href="/css/couplet.css">
  </head>

  <body>
    <div>

      <div class="input-container">
        <input id="inputFirstCouplet" size="25" placeholder="请输入上联" name="firstCouplet" type="text" />
        <button class="button-red">求下联</button>
      </div>

      <table id="preview">
        <tbody>
          <tr>
            <td colspan="3">
              <table>
                <tbody>
                  <tr>
                    <td style="width: 16px;"></td>

                    <!-- 上联 -->
                    <td>
                      <div id="firstCouplet">
                        <table style="margin-left: auto; margin-right: auto;">
                          <tbody>
                          </tbody>
                        </table>
                      </div>
                    </td>

                    <td style="width: 64px;"></td>

                    <!-- 下联 -->
                    <td>
                      <div id="secondCouplet">
                        <table style="margin-left: auto; margin-right: auto;">
                          <tbody>
                          </tbody>
                        </table>
                      </div>
                    </td>

                    <td style="width: 16px;"></td>
                  </tr>
                </tbody>
              </table>
            </td>
          </tr>
        </tbody>
      </table>
      
    </div>

    <!-- LOADING -->
    <div class="spinner">
      <div class="rect1"></div>
      <div class="rect2"></div>
      <div class="rect3"></div>
      <div class="rect4"></div>
      <div class="rect5"></div>
    </div>
    
  </body>

  <script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
  <script type="text/javascript" src="js/couplet.js"></script>

</html>
